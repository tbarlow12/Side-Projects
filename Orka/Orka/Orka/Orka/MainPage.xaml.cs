using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;

namespace Orka
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();
            welcomeMessage.Text = string.Format("Welcome {0} to Orka", App.CurrentUser.Username);
        }

        private PlotModel CreatePlotModel()
        {
            var plotModel = new PlotModel { Title = "OxyPlot Demo" };

            plotModel.Axes.Add(new LinearAxis { Position = AxisPosition.Bottom });
            plotModel.Axes.Add(new LinearAxis { Position = AxisPosition.Left, Maximum = 10, Minimum = 0 });

            var series1 = new LineSeries
            {
                MarkerType = MarkerType.Circle,
                MarkerSize = 4,
                MarkerStroke = OxyColors.White
            };
            int i = 1;
            foreach(DrinkData drink in Constants.Drinks)
            {
                series1.Points.Add(new DataPoint(i++,drink.Amount));
            }


            plotModel.Series.Add(series1);

            return plotModel;
        }

        async void OnLogoutButtonClicked(object sender, EventArgs e)
        {
            App.IsUserLoggedIn = false;
            Navigation.InsertPageBefore(new LoginPage(), this);
            await Navigation.PopAsync();
        }
    }
}
