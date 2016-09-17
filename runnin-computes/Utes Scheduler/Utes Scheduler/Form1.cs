using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace Utes_Scheduler
{
    public partial class GUI : Form
    {
        public GUI()
        {
            InitializeComponent();
            File course_info = new File("");
        }
    }
}
