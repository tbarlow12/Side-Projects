using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Orka
{
    public static class Constants
    {
        public static string Username = "Tanner";
        public static string Password = "password";
        public static DrinkData[] Drinks = new DrinkData[]
        {
            new DrinkData
            {
                Username = Constants.Username,
                Amount = 40.0,
                Datetime = DateTime.Now
            },
            new DrinkData
            {
                Username = Constants.Username,
                Amount = 60.0,
                Datetime = DateTime.Now
            },
            new DrinkData
            {
                Username = Constants.Username,
                Amount = 80.0,
                Datetime = DateTime.Now
            },
            new DrinkData
            {
                Username = Constants.Username,
                Amount = 70.0,
                Datetime = DateTime.Now
            },
            new DrinkData
            {
                Username = Constants.Username,
                Amount = 20.0,
                Datetime = DateTime.Now
            }
        };
    }
}
