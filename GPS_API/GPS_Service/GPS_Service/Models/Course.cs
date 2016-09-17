using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Globalization;

namespace GPS_Service.Models
{
    public class Course
    {
        public int CourseId { get; set; }
        public string Name { get; set; }
        public string Professor { get; set; }
        public int Catalog { get; set; }
        public string Self {
            get {
                return string.Format(CultureInfo.CurrentCulture,
               "api/courses/{0}", this.CourseId);
            }

                set { }
        }

    }
}