using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace ACMTryouts
{
    class Problem1
    {
        static void Main(string[] args)
        {
            int solved = 0;
            int time = 0;
            Dictionary<string, int> scores = new Dictionary<string, int>();

            string line;

            // Read the file and display it line by line.
            using (System.IO.StreamReader file = new System.IO.StreamReader(args[0]))
            {
                while ((line = file.ReadLine()) != null)
                {
                    string[] a = line.Split(' ');
                    if (a.Length == 3)
                    {
                        AddScore(scores, a);
                    }
                }
            }
            foreach(KeyValuePair<string,int> kvp in scores)
            {
                if(kvp.Value > 0)
                {
                    time += kvp.Value;
                    solved++;
                }
            }
            Console.WriteLine(string.Format("{0} {1}", solved, time));
            Console.ReadLine();
        }

        public static void AddScore(Dictionary<string, int> d, string[] a)
        {
            int value = int.Parse(a[0]);
            string problem = a[1];
            if (!a[2].Equals("right"))
                value = -value;
            if (d.ContainsKey(problem))
            {
                d[problem] += value;
            }
            else
            {
                d.Add(problem, value);
            }                
        }
    }
}
