using System;
using System.Collections.Generic;
using System.Text;
using Jypeli;
using Jypeli.Assets;
using Jypeli.Controls;
using Jypeli.Widgets;
using Jypeli.Effects;

///@author Said-Aga Shah-Aga
///@version 21.4.2020 

/// <summary>
/// Pelaaja niminen luokka, jolle on asetettu myös elämät. Pelaaja-luokka on perinyt ominaisuudet fysiikkaoliolta.
/// </summary>
public class Pelaaja : PhysicsObject
    {
        public IntMeter Elamat;
        public Pelaaja(double leveys, double korkeus, int elamat)
            : base(leveys, korkeus)
        {
            Elamat = new IntMeter(elamat, 0, elamat);
            Elamat.LowerLimit += delegate () { this.Destroy(); };
        }

    }

