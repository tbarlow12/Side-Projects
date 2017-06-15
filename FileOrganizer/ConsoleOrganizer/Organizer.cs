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
            CopyPaths(paths, target_path);
            Console.WriteLine("Done");
        }

        private static string GetMonthString(this int month)
        {
            switch (month)
            {
                case 1:
                    return "January";
                case 2:
                    return "February";
                case 3:
                    return "March";
                case 4:
                    return "April";
                case 5:
                    return "May";
                case 6:
                    return "June";
                case 7:
                    return "July";
                case 8:
                    return "August";
                case 9:
                    return "September";
                case 10:
                    return "October";
                case 11:
                    return "November";
                case 12:
                    return "December";
                default:
                    return "";
            }
        } 


        private static void CopyPaths(Dictionary<int, Dictionary<int, List<string>>> paths, string target_path)
        {
            throw new NotImplementedException();
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
