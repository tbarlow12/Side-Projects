using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ConsoleOrganizer
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            var source_path_dialog = new FolderBrowserDialog();
            DialogResult source_result = source_path_dialog.ShowDialog();
            if(source_result == DialogResult.OK)
            {
                var source_path = source_path_dialog.SelectedPath;
                var dest_dialog = new FolderBrowserDialog();
                DialogResult dest_result = dest_dialog.ShowDialog();
                if (dest_result == DialogResult.OK)
                {
                    var dest_path = dest_dialog.SelectedPath;
                    Organizer.Organize(source_path, dest_path, null);
                }
            }
            
        }
    }
}
