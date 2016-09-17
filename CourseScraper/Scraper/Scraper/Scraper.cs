using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HtmlAgilityPack;
using System.Net;

namespace Scraper
{
    class StaticScraper
    {
        static WebScraper scraper;
        static void Main(string[] args)
        {
            scraper = new WebScraper();
            Dictionary<string,string> majors = scraper.ScrapeMajors("https://student.apps.utah.edu/uofu/stu/ClassSchedules/main/1168/index.html");
            //scraper.WriteMajorsToDatabase(majors);
            scraper.ScrapeCoursesAllMajors(majors);
            Console.ReadLine();
        }
    }  
}
