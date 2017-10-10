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
            var path_count = GetPathCount(paths);
            foreach(var year_kvp in paths)
            {
                int year = year_kvp.Key;
                var year_dict = year_kvp.Value;
                foreach(var month_kvp in year_dict)
                {
                    int month = month_kvp.Key;
                    var path_list = month_kvp.Value;
                    double i = 1;
                    foreach(var path in path_list)
                    {
                        var name = Path.GetFileNameWithoutExtension(path);
                        var ext = Path.GetExtension(path);
                        var new_dir = string.Format(@"{0}/{1}/{2}/", target_path, year, month);
                        var full_name = string.Format(@"{0}{1}", name, ext);
                        var new_path = CopyNewPath(path, new_dir, name, ext);
                        double percentage_done = (i / path_count) * 100;
                        Console.WriteLine(String.Format("{0:0.00}%-{1}", percentage_done, new_path));
                    }                    
                }
            }
        }

        private static double GetPathCount(Dictionary<int, Dictionary<int, List<string>>> paths)
        {
            double count = 0;
            foreach(var y in paths.Values)
            {
                foreach(var m in y.Values)
                {
                    count += m.Count;
                }
            }
            return count;
        }

        private static string CopyNewPath(string source_path, string new_dir, string name, string ext)
        {
            var new_path = new_dir + name + ext;
            if (Directory.Exists(new_dir))
            {
                int index = 1;
                while (File.Exists(new_path))
                {
                    name += string.Format("-duplicate-{0}", index);
                    new_path = new_dir + name + ext;
                    index += 1;
                }
            }
            else
            {
                Directory.CreateDirectory(new_dir);
            }
            File.Copy(source_path, new_path);
            return new_path;
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
