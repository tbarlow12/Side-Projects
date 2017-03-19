using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleOrganizer
{
    class Program
    {
        static void Main(string[] args)
        {
            Organizer.Organize(@"C:\Users\Tanner\Google Drive", @"C:\Users\Tanner\Pictures\OrganizerTest", new List<string> { ".jpg" });
        }
    }
}
