using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HtmlAgilityPack;
using System.Net;
using Model;

namespace Scraper
{
    /* Database Schema
     * 
     * CPUofU_CoursePlanner
     * 
     * majors(abbrev,name)
     * courses(dept,catalog) - dept references abbrev
     * 
     * */
    class WebScraper
    {
        private DatabaseConnection db;
        public Dictionary<string, string> ScrapeMajors(string url)
        {
            Dictionary<string, string> majors = new Dictionary<string, string>();
            HtmlWeb web = new HtmlWeb();
            HtmlDocument doc = new HtmlDocument();
            doc.OptionFixNestedTags = true;
            doc = web.Load(url);
            if (doc.ParseErrors != null && doc.ParseErrors.Count() > 0)
            {
                Console.WriteLine("Got errors");
            }
            else
            {
                if (doc.DocumentNode != null)
                {
                    HtmlNode body = doc.DocumentNode.SelectSingleNode("//body");
                    if (body != null)
                    {
                        string classToFind = "subject-list";
                        IEnumerable<HtmlNode> nodes = doc.DocumentNode.SelectNodes(string.Format("//*[contains(@class,'{0}')]", classToFind));
                        foreach (HtmlNode node in nodes)
                        {
                            HtmlNode abbrevNode = node.ChildNodes[1].FirstChild;
                            HtmlNode nameNode = abbrevNode.NextSibling;
                            string abbreviation = abbrevNode.InnerText;
                            string name = nameNode.InnerText;
                            majors.Add(abbreviation, name);
                        }
                    }
                }
            }
            return majors;
        }

        //INSERT INTO `cpuofu_courseplanner`.`sections_fall16` (`id`, `dept`, `catalog`, `number`, `type`, `credits`, `title`, `schedule`, `location`, `attributes`, `professor`, `fees`) VALUES ('1', 'cs', '1400', '001', 'lecture', '3.0', 'Intro to CS', 'T,H 9:10-10:30', 'WEB L130', 'flxu', 'zachary', '150.50')
        //ON DUPLICATE KEY UPDATE dept = 'CS', catalog = '1400', number = '', type = 'lecture', credits = '3.0', title = 'Intro to CS', schedule = 'T,H 9:10-10:30', location = 'WEB L130', attributes = 'FLXU', professor = 'ZACHARY', fees = '150.50';
        //INSERT INTO `cpuofu_courseplanner`.`courses` (`dept`, `catalog`) VALUES ('cs', '1400');

        public void WriteSectionsToDatabase(List<Section> sections)
        {
            db = new DatabaseConnection();
            if (db.Connect())
            {
                StringBuilder sql = new StringBuilder();
                foreach (Section s in sections)
                {
                    
                    sql.Append("INSERT INTO `cpuofu_courseplanner`.`sections_fall16` (`id`, `dept`, `catalog`, `number`, `type`, `credits`, `schedule`, `location`, `attributes`, `professor`, `fees`) " +
                        "VALUES ('" + s.SectionId + "', '" + s.Dept + "', '" + s.CatalogNumber + "', '" + s.SectionNumber + "', '" + s.SectionType + "', '" + s.Credits + 
                        "', '" + s.Schedule + "', '" + s.Location + "', '" + s.Attributes + "', '" + s.Professor + "', '" + s.Fees + "') " +
                        "ON DUPLICATE KEY UPDATE dept = '" + s.Dept + "', catalog = '" + s.CatalogNumber + "', number = '" + s.SectionNumber + "', type = '" + s.SectionType +
                        "', credits = '" + s.Credits + "', schedule = '" + s.Schedule + "', location = '" + s.Schedule + "', attributes = '" + s.Attributes + 
                        "', professor = '" + s.Professor + "', fees = '" + s.Fees + "';");
                }
                db.ExecuteUpdate(sql.ToString());
                db.Close();
            }
        }

        public void WriteCoursesToDatabase(Dictionary<string,Section> courses)
        {
            db = new DatabaseConnection();
            if (db.Connect())
            {
                StringBuilder sql = new StringBuilder();
                foreach (Section c in courses.Values)
                {
                    sql.Append("INSERT IGNORE INTO `cpuofu_courseplanner`.`courses` (`dept`, `catalog`, `title`) VALUES ('" + c.Dept + "', '" + c.CatalogNumber + "', '" + c.CourseTitle + "');");
                }
                db.ExecuteUpdate(sql.ToString());
                db.Close();
            }
        }

        public void WriteMajorsToDatabase(Dictionary<string, string> majors)
        {
            db = new DatabaseConnection();
            if (db.Connect())
            {
                StringBuilder sql = new StringBuilder();
                foreach(KeyValuePair<string,string> major in majors)
                {
                    string abbrev = major.Key;
                    string name = major.Value;
                    sql.Append("INSERT IGNORE INTO `cpuofu_courseplanner`.`majors` (`abbrev`, `name`) VALUES ('" + abbrev + "', '" + name + "');");
                }
                db.ExecuteUpdate(sql.ToString());
                db.Close();
            }
        }

        public void ScrapeCoursesAllMajors(Dictionary<string, string> majors)
        {
            foreach (string major in majors.Keys)
            {
                Console.WriteLine("Scraping: " + major);
                ScrapeCourses(major);
            }
        }

        public void ScrapeCourses(string major)
        {
            string url = "https://student.apps.utah.edu/uofu/stu/ClassSchedules/main/1168/class_list.html?subject=" + major;
            if (major.Equals("ESSF"))
                url = "https://student.apps.utah.edu/uofu/stu/ClassSchedules/main/1168/class_list.html?subject=ESSF&credit=Y&type=&req=&session=&location=";
            HtmlWeb web = new HtmlWeb();
            HtmlDocument doc = new HtmlDocument();
            doc.OptionFixNestedTags = true;
            doc = web.Load(url);
            if (doc.DocumentNode != null)
            {
                HtmlNode body = doc.DocumentNode.SelectSingleNode("//body");
                if (body != null)
                {
                    string classToFind = "even";
                    List<Section> sections = new List<Section>();
                    Dictionary<string, Section> courses = new Dictionary<string, Section>();
                    IEnumerable<HtmlNode> evenNodes = doc.DocumentNode.SelectNodes(string.Format("//*[contains(@class,'{0}')]", classToFind));
                    classToFind = "odd";
                    IEnumerable<HtmlNode> oddNodes = doc.DocumentNode.SelectNodes(string.Format("//*[contains(@class,'{0}')]", classToFind));
                    ProcessSections(sections, courses, evenNodes);
                    ProcessSections(sections, courses, oddNodes);
                    WriteCoursesToDatabase(courses);
                    //WriteSectionsToDatabase(sections);
                    Console.WriteLine("Found " + sections.Count + " sections and " + courses.Count + " courses");
                }
            }
        }

        private void ProcessSections(List<Section> sections, Dictionary<string,Section> courses, IEnumerable<HtmlNode> nodes)
        {
            if (nodes == null)
                return;
            foreach (HtmlNode node in nodes)
            {
                HtmlNodeCollection children = node.ChildNodes;
                Section section = new Section();
                int sectionId;
                if(int.TryParse(children[1].InnerText, out sectionId))
                {
                    section.SectionId = sectionId;
                }
                else
                {
                    continue;
                }
                section.Dept = GetChild(children, 3, 1); //children[3].ChildNodes[1].InnerText;
                section.CatalogNumber = int.Parse(GetChild(children, 5, 1));// children[5].ChildNodes[1].InnerText);
                section.SectionNumber = int.Parse(GetChild(children, 7, 0));// children[7].FirstChild.InnerText);
                section.SectionType = GetChild(children, 9, 0);// children[9].FirstChild.InnerText;
                section.CreditString = GetChild(children, 11, 0);// children[11].FirstChild.InnerText;
                double credits;
                if(double.TryParse(section.CreditString, out credits))
                {
                    section.Credits = credits;
                }
                section.CourseTitle = GetChild(children, 13, 1); //children[13].ChildNodes[1].InnerText;
                section.Schedule = GetChild(children, 15, 1); //children[15].ChildNodes[1].InnerText;
                section.Location = GetChild(children, 17, 1, 1);// children[17].ChildNodes[1].ChildNodes[1].InnerText;
                section.Attributes = GetChild(children, 19, 1); //children[19].ChildNodes[1].InnerText.Trim();
                section.Professor = GetChild(children, 21, 1); //children[21].ChildNodes[1].InnerText.Trim();
                string fees = GetChild(children, 27);// children[27].InnerText.Trim();
                if (fees != null && fees.Length > 0)
                    section.Fees = fees.Substring(1);
                sections.Add(section);
                if (!courses.ContainsKey(section.Key))
                {
                    courses.Add(section.Key, section);
                }              
            }
        }

        

        private string GetChild(HtmlNodeCollection children, int child_index)
        {
            if (children.Count > child_index)
                return children[child_index].InnerText.Trim();
            return string.Empty;
        }

        private string GetChild(HtmlNodeCollection children, int child_index, int grand_index)
        {
            if(children.Count > child_index && children[child_index].ChildNodes.Count > grand_index)
            {
                return children[child_index].ChildNodes[grand_index].InnerText.Trim();
            }
            return string.Empty;
        }

        private string GetChild(HtmlNodeCollection children, int child_index, int grand_index, int great_index)
        {
            if (children.Count > child_index && children[child_index].ChildNodes.Count > grand_index &&
                children[child_index].ChildNodes[grand_index].ChildNodes.Count > great_index)
            {
                return children[child_index].ChildNodes[grand_index].ChildNodes[great_index].InnerText.Trim();
            }
            return string.Empty;
        }

        public void ScrapePreReqs()
        {

        }
    }
}
