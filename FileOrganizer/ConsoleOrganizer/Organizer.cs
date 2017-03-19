using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace ConsoleOrganizer
{
    static class Organizer
    {
 
        public static void Organize(string root_path, string target_path, List<string> file_types)
        {
            var paths = GetPaths(root_path,file_types);
            Console.WriteLine("Done");
        }

        internal static Dictionary<int,Dictionary<int,List<string>>> GetPaths(string root_path,List<string> file_types)
        {
            var paths = GetFilePaths(root_path, file_types);


            return OrganizePaths(paths);
        }

        internal static IEnumerable<string> GetFilePaths(string root_path, List<string> file_types)
        {
            if(file_types == null || file_types.Count == 0)
            {
                return Directory.GetFiles(root_path, "*.*", SearchOption.AllDirectories);
            }
            return Directory.GetFiles(root_path, "*.*", SearchOption.AllDirectories).Where(s => file_types.Contains(Path.GetExtension(s).ToLower()));
        }

        internal static Dictionary<int, Dictionary<int, List<string>>> OrganizePaths(IEnumerable<string> paths)
        {
            var result = new Dictionary<int, Dictionary<int, List<string>>>();
            
            foreach(string path in paths)
            {
                DateTime creation = File.GetCreationTime(path);
                DateTime modification = File.GetLastWriteTime(path);
                var year = modification.Year;
                var month = modification.Month;              

                if (result.ContainsKey(year))
                {
                    var year_dict = result[year];

                    if (year_dict.ContainsKey(month))
                    {
                        year_dict[month].Add(path);
                    }
                    else
                    {
                        var month_list = new List<string> { path };
                        year_dict[month] = month_list;
                    }
                }
                else
                {
                    var year_dict = new Dictionary<int, List<string>>();
                    var month_list = new List<string> { path };
                    year_dict[month] = month_list;
                    result[year] = year_dict;
                }
            }
            return result;
        }
    }
}
