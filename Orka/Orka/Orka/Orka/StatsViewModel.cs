using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections.ObjectModel;

namespace Orka
{
    class StatsViewModel
    {
        public ObservableCollection<DrinkData> DrinkCollection { get; set; }
        public StatsViewModel()
        {
            DrinkCollection = new ObservableCollection<DrinkData>();
        }
    }
}
