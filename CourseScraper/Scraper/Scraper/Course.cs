using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    class Section
    {
        public int SectionId { get; set; }
        public string Dept { get; set; }
        public int CatalogNumber { get; set; }
        public int SectionNumber { get; set; }
        public string SectionType { get; set; }
        public string CreditString { get; set; }
        public double Credits { get; set; }
        public string CourseTitle { get; set; }
        public string Schedule { get; set; }
        public string Location { get; set; }
        public string Attributes { get; set; }
        public string Professor { get; set; }
        public string Fees { get; set; }
        public string Key
        {
            get
            {
                return Dept + CatalogNumber;
            }
        }
    }

    class Requirement
    {
    }

    class Professor
    {
    }
}
