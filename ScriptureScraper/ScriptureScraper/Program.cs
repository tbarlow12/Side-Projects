using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using HtmlAgilityPack;

namespace ScriptureScraper
{
    class Program
    {
        static void Main(string[] args)
        {

            ScrapeTopicalGuide();
        }

        private static List<Tuple<string,string>> GetLetterLinks(string url, char letter)
        {
            HtmlWeb web = new HtmlWeb();
            List<Tuple<string, string>> tuples = new List<Tuple<string, string>>();
            HtmlDocument doc = new HtmlDocument();
            doc.OptionFixNestedTags = true;
            doc = web.Load(url);
            if (doc.DocumentNode != null)
            {
                var links = doc.DocumentNode.Descendants("ul").Where(d =>
                    d.Attributes.Contains("class") &&
                    d.Attributes["class"].
                    Value.Contains("topics guide")).
                    First().ChildNodes;

                foreach (HtmlNode node in links)
                {
                    foreach (HtmlNode child in node.ChildNodes)
                    {
                        if (child.Name.Equals("a"))
                        {
                            var link = child.Attributes.First().Value;
                            var name = child.InnerText;
                            tuples.Add(new Tuple<string, string>(name, link));
                        }
                    }
                }
            }
            return tuples;
        }

        private static void ScrapeTopicalGuide()
        {
            string url = "https://www.lds.org/scriptures/tg?lang=eng";
            List<Tuple<string, string>> links = new List<Tuple<string, string>>();
            for (char letter = 'a'; letter <= 'z'; letter++)
            {
                links.AddRange(GetLetterLinks(url, letter));
            }
            foreach(var link in links)
            {
                Console.WriteLine(link);
            }
            Console.ReadLine();
        }
    }
}
