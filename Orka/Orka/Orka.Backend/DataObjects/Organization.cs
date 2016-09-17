using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Orka.Backend.DataObjects
{
    public class Organization
    {
        public int OrgId { get; set; }
        public string Name { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime Expiration { get; set; }
    }
}