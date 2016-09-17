using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using Windows.UI.Core;
using System.Threading;

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=402352&clcid=0x409

namespace SpheroProject
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        SpheroManager sp;
        Timer minTimer;
        Timer secTimer;
        public MainPage()
        {
            sp = new SpheroManager();
            DataContext = sp;           


            this.InitializeComponent();
            
            CoreWindow.GetForCurrentThread().KeyDown += MainPage_KeyDown;
            CoreWindow.GetForCurrentThread().KeyUp += MainPage_KeyUp;
          
        }

       

        private void MainPage_KeyUp(CoreWindow sender, KeyEventArgs args)
        {

            if (sp.m_robot != null)
            {
                //sp.m_robot.Roll(0, 0f);
            }
        }

        private void MainPage_KeyDown(CoreWindow sender, KeyEventArgs args)
        {
            /*
            if (sp.m_robot != null)
            {
                switch (args.VirtualKey)
                {
                    case Windows.System.VirtualKey.Up:
                        //roll forward
                        sp.m_robot.Roll(0, (float)sldSpeed.Value);
                        break;
                    case Windows.System.VirtualKey.Down:
                        sp.m_robot.Roll(180, (float)sldSpeed.Value);
                        break;
                    case Windows.System.VirtualKey.Left:
                        sp.m_robot.Roll(270, (float)sldSpeed.Value);
                        break;
                    case Windows.System.VirtualKey.Right:
                        sp.m_robot.Roll(90, (float)sldSpeed.Value);
                        break;

                }
               

            }
            */
                
           

        }

        private  void ConnectionToggle_Toggled(object sender, RoutedEventArgs e)
        {

            ConnectionToggle.OnContent = "Connecting...";
            if (ConnectionToggle.IsOn)
            {
                if (sp.m_robot==null)
                {
                    sp.SetupRobotConnection();
                    grdControls.Visibility = Visibility.Visible;
                 
                }
            }
            else
            {
                sp.ShutdownRobotConnection();
                grdControls.Visibility = Visibility.Collapsed;
            }
            
        }

        private void sldHeading_ValueChanged(object sender, RangeBaseValueChangedEventArgs e)
        {
            /*
            if (sp.m_robot != null)
            {
                sp.m_robot.SetBackLED(1f);
                sp.m_robot.Roll((int)sldHeading.Value, 0f);
               
                sp.m_robot.SetBackLED(0f);
            }
            */
        }

        private void sldRed_ValueChanged(object sender, RangeBaseValueChangedEventArgs e)
        {
            setColor();
        }

        private void sldGreen_ValueChanged(object sender, RangeBaseValueChangedEventArgs e)
        {
            setColor();
        }

        private void sldBlue_ValueChanged(object sender, RangeBaseValueChangedEventArgs e)
        {
            setColor();
        }

        void setColor()
        {
            /*
            if (sp.m_robot != null)
                sp.m_robot.SetRGBLED((int)sldRed.Value, (int)sldGreen.Value, (int)sldBlue.Value);
                */
        }

        private void sldHeading_PointerCaptureLost(object sender, PointerRoutedEventArgs e)
        {
            /*
            if (sp.m_robot != null)
            {
                sp.m_robot.SetHeading(0);
                sldHeading.Value = 0;
            }
            */
        }

        private void textBox_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void startTimer_Click(object sender, RoutedEventArgs e)
        {
            
            
        }

        private void StartTimer_Toggled(object sender, RoutedEventArgs e)
        {
            if (startTimer.IsOn)
            {
                double minutes = sldMinutes.Value;
                double seconds = sldSeconds.Value;

                int total = ((int)minutes * 60) + (int)seconds;
                /*
                if (minutes > 0)
                    minTimer = new Timer(DecreaseMinutes, null, 0, 60 * 1000);
                if (seconds > 0)
                    secTimer = new Timer(DecreaseSeconds, null, 0, 1000);
                    */
                sp.Pomosphero(total);
            }
            else
            {
                sp.StopTimer();
            }
            
        }

        private void DecreaseMinutes(object state)
        {
            lock (sldMinutes)
            {
                if (sldMinutes.Value > 0)
                    sldMinutes.Value--;
            }
        }

        private void DecreaseSeconds(object state)
        {
            lock(sldSeconds)
            {
                if (sldSeconds.Value > 0)
                    sldSeconds.Value--;
            }
        }

        private void EnableControls(object state)
        {
            /*
            sldBlue.IsEnabled = true;
            sldRed.IsEnabled = true;
            sldGreen.IsEnabled = true;
            startTimer.IsEnabled = true;*/
        }
    }
}
