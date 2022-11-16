using Jypeli;
using Jypeli.Assets;
using Jypeli.Widgets;
using System;
using System.Collections.Generic;

/// @author Said-Aga Shah-Aga
/// @version 21.4.2020
/// 
/// <summary>
/// WolfensteinSpace64-peli
/// Avaruuspelissa luodaan pelaaja ja viholliset, joita yritetaan ampua ja saada pisteita
/// tietyn maaran pisteidensaannin jalkeen ilmestyy loppuvihu, jonka tuhoamalla voitat pelin.
/// Loppupisteet tallentuvat parhaatPisteet taulukkoon. 
/// </summary>
public class WolfensteinSpace64 : PhysicsGame
{ 
    private double scrollausnopeus = -10.0;
    private GameObject ekaTaustakuva;
    private IntMeter pisteLaskuri;
    private EasyHighScore parhaatPisteet = new EasyHighScore();
    private Timer ampumisAjastin;
    private Timer tahtiTimer;
    private Timer vihuTimer;
    private Timer paavihuTimer;
    private int[] vihunteot = new[] { 4, 2, 1, 10 };
    private int[] paavihunteot = new[] { 50, 30, 20, int.MaxValue};
    private int[] elamatahdenteot = new[] { 3, 7, 15, 20};
    private int[] pisterajat = new[] { 100, 200, 300, int.MaxValue};
    private int vaikeusTaso = 0;

    /// <summary>
    /// Begin ohjelma aloittaa pelin
    /// </summary>
    public override void Begin()
    {
        SetWindowSize(1200, 900);
        AlkuValikko();
    }


    /// <summary>
    /// AlkuValikko aliohjelma avaa alkuvalikon, josta paasee aloittamaan pelin
    /// </summary>
    private void AlkuValikko()
    {
        ClearAll();
        MultiSelectWindow alkuValikko = new MultiSelectWindow("WolfensteinSpace64",
        "Aloita peli", "Parhaat pisteet", "Lopeta");
        alkuValikko.Color = Color.White;
        Add(alkuValikko);
       
        alkuValikko.AddItemHandler(0, AlkuKuvaus);
        alkuValikko.AddItemHandler(1, ParhaatPisteet);
        alkuValikko.AddItemHandler(2, Exit);
        MediaPlayer.Play("AvaruusTeema");
        MediaPlayer.IsRepeating = true;
        Level.Background.Image = Level.Background.CreateStars(1000);
    }


    /// <summary>
    /// Aliohjelma, jolla voidaan rakentaa tekstit esim. ennen pelin alkua, kun pelaaja haviaa tai voittaa. 
    /// </summary>
    /// <param name="leveys">Tekstikentan leveys</param> 
    /// <param name="korkeus">Tekstikentan korkeus</param> 
    /// <param name="teksti">Varsinainen teksti, joka nakyy ruudulla</param> 
    /// <param name="KuvauksenTaustanVari">Tekstin taustan vari</param> 
    /// <param name="KuvauksenTekstinVari">Tekstin varsinainen vari</param> 
    /// <param name="sijaintiX">Tekstin sijainti X akselilla</param> 
    /// <param name="sijaintiY">Tekstin sijainti Y akselilla</param> 
    private void TarinanKuvaus(double leveys, double korkeus, string teksti, Color KuvauksenTaustanVari, Color KuvauksenTekstinVari, int sijaintiX, int sijaintiY)
    {
        Label kuvaus = new Label(leveys, korkeus, teksti);
        kuvaus.Color = KuvauksenTaustanVari;
        kuvaus.TextColor = KuvauksenTekstinVari;
        kuvaus.Position = new Vector(sijaintiX, sijaintiY);
        Add(kuvaus);
    }


    /// <summary>
    /// AlkuKuvaus niminen alkuohjelma, joka tuottaa ennen varsinaista pelia aloitustekstin ruudulle. 
    /// </summary>
    private void AlkuKuvaus ()
    {
        Level.Background.Image = Level.Background.CreateStars(1000);
        Timer.SingleShot (1.0, delegate () { TarinanKuvaus(Level.Width, Level.Height, "Tehtäväsi on pelastaa maailma vaarallisilta avaruusÖykkäreiltä!", Color.Transparent, Color.Turquoise, 0, 50); } );
        Timer.SingleShot(3.0, delegate () {TarinanKuvaus (Level.Width, Level.Height, "Tuhoa vihollisia, saa pisteitä ja voita lopussa UberPahis! Vain sinä voit tehdä sen!", Color.Transparent, Color.Red, 0, 0); } );
        Timer.SingleShot(5.0, delegate () {TarinanKuvaus (Level.Width, Level.Height, "Saa pisteitä ja etene pelissä. Kun olet tuhonnut tarpeeksi vastustajia UberPahis ilmestyy paikalle!", Color.Transparent, Color.Blue, 0, -50); } );
        Timer.SingleShot(7.0, delegate () {TarinanKuvaus (Level.Width, Level.Height, "Paina Enter aloittaaksesi pelin", Color.Transparent, Color.White, 0, -100); } );
        MediaPlayer.Play("AvaruusTeema");
        
        Keyboard.Listen(Key.Enter, ButtonState.Pressed, AloitaPeli, "Aloittaa pelin");   
    }


    /// <summary>
    /// Pelin varsinainen aloitusohjelma, joka kutsuu aliohjelmaa AloitaPeliVaikeustasolla. 
    /// </summary>
    private void AloitaPeli()
    {
        ClearAll();
        vaikeusTaso = 0;
        AloitaPeliVaikeustasolla();
    }


    /// <summary>
    /// Aloitetaan peli kolmella eri parametrilla.
    /// </summary>
    private void AloitaPeliVaikeustasolla()
    {
        AloitaPeli(vihunteot[vaikeusTaso], paavihunteot[vaikeusTaso], elamatahdenteot[vaikeusTaso]);
    }


    /// <summary>
    /// Kutsutaan LuoKentta ohjelmaa tassa pelissa. 
    /// </summary>
    /// <param name="vihunteko">int-tyyppinen parametri, joka maarittelee kuinka nopeasti luodaan normivihuja</param> 
    /// <param name="paavihunteko">int-tyyppinen parametri, joka maarittelee kuinka nopeasti luodaan vahvempia vihuja</param> 
    /// <param name="elamatahdenteko">int- tyyppinen parametri, joka maarittelee kuinka nopeasti luodaan elamatahtia</param>
    private void AloitaPeli(int vihunteko, int paavihunteko, int elamatahdenteko)
    {
        LuoKentta(vihunteko, paavihunteko, elamatahdenteko);
        LuoPistelaskuri(0, Level.Right - 100, Level.Bottom + 100, Color.White, Color.Transparent, "pisteet/eteneminen", 3);
        LuoTaustakuvat(0.015, 0, 5, 1200, 1200, 7000);
    }


    /// <summary>
    /// Aloita alusta aliohjelma, jota kutsutaan
    /// </summary>
    /// <param name="sender"> HighScoreWindowin yhteydessa kaytettava parametri,
    /// jolla voidaan parhaat pisteet ikkunan sulkemisen siirtya allaolevaan aliohjelmaan ja sita kautta pelin alkuun</param>
    private void AloitaAlusta(Window sender)
    {
        Begin();
    }
   

    /// <summary>
    /// Aliohjelma, jota kutsutaan kun UberPahis on tuhottu. 
    /// </summary>
    private void VoititPelin()
    {
        ClearAll();
        TarinanKuvaus(Level.Width, Level.Height, "ONNEKSI OLKOON! KUKISTIT ILKEÄT VIHOLLISET JA PELASTIT MAAILMAN!", Color.Transparent, Color.White, 0, 0);
        TarinanKuvaus(Level.Width, Level.Height, "Paina ESC lopettaaksesi!", Color.Transparent, Color.White, 0, 50);
        MediaPlayer.Stop();
        Keyboard.Listen(Key.Escape, ButtonState.Pressed, LopetaVoitettuPeli, "Lopettaa pelin ja syöttää pisteet");
    }


    /// <summary>
    /// Lopettaa voitetun pelin ja antaa syottaa pisteet taulukkoon, mikali ne riittavat 
    /// </summary>
    private void LopetaVoitettuPeli()
    {
        parhaatPisteet.EnterAndShow(pisteLaskuri.Value);
        parhaatPisteet.HighScoreWindow.Closed += AloitaAlusta;
    }


    /// <summary>
    /// Luo loppuvihollisen peliin sekä kaynnistaa loppuvihollisen yhteydessa soivan EEPPISEN musiikin. 
    /// </summary>
    private void LoppuTaso()
    {
        LuoUberVihu();
        MediaPlayer.Play("BossiTeema");
        MediaPlayer.IsRepeating = true;
    }


    /// <summary>
    /// MultiSelectWindowin osa. Nayttaa parhaat pisteet.
    /// </summary>
    private void ParhaatPisteet()
    {
        parhaatPisteet.Show();
        parhaatPisteet.HighScoreWindow.Closed += AloitaAlusta;
    }


    /// <summary>
    /// Luo kentan, pelialuksen, viholliset ja musiikin peliin.
    /// </summary>
    /// <param name="vihunteko">parametri, jolla maarataan kuinka nopeasti vihollinen ilmaantuu kentalle</param> 
    /// <param name="paavihunteko">parametri, jolla maarataan kuinka nopeasti voimakkaampi vihollinen ilmaantuu kentalle</param> 
    /// <param name="elamatahdenteko">parametri, jolla maarataan kuinka nopeasti elamatahti ilmaantuu kentalle</param> 
    private void LuoKentta(int vihunteko, int paavihunteko, int elamatahdenteko)
    {
        TeeAlus(0, Level.Bottom + 100, 30 , 100, "Avaruusalus", Key.Left, Key.Right, Key.Up, Key.Down, Key.Space, 100, Level.Left + 100, Level.Bottom + 100, 200, 25, Color.Green, 3, 0.1, 500, 0);
        vihuTimer = Timer.CreateAndStart(vihunteko, LuoVihu);
        paavihuTimer = Timer.CreateAndStart(paavihunteko, LuoPaaVihu);
        tahtiTimer = Timer.CreateAndStart(elamatahdenteko, LuoElamaTahti);
        Level.CreateHorizontalBorders(1.0, false);
        Level.CreateBottomBorder(1.0, false);
        MediaPlayer.Play("Avaruus2");
        MediaPlayer.IsRepeating = true;
    }


    /// <summary>
    /// Aliohjelma luo pelikentalle liikuteltavan aluksen
    /// </summary>
    /// <param name="x">x-koordinaatti, johon luodaan pelaaja</param> 
    /// <param name="y">y-koordinaatti, johon luodaan pelaaja</param> 
    /// <param name="koko">Asetetaan alukselle koko</param> 
    /// <param name="elamat">Asetetaan alukselle elamat</param> 
    /// <param name="latauskohde">Contentista otettu kuva, voi kayttaa myos muista kuvia</param> 
    /// <param name="vasen">Asetetaan nappain, jota painettaessa liikkuu vasemmalle</param> 
    /// <param name="oikea">Asetetaan nappain, jota painettaessa liikkuu oikealle</param> 
    /// <param name="ylos">Asetetaan nappain, jota painettaessa liikkuu ylos</param> 
    /// <param name="alas">Asetetaan nappain, jota painettaessa liikkuu alas</param> 
    /// <param name="ammu">Asetetaan nappain, jota painettaessa ampuu</param> 
    /// <param name="maksimielama">Asetetaan alukselle maksimielama</param> 
    /// <param name="palkinX">elamapalkin x-koordinaatti</param> 
    /// <param name="palkinY">elamapalkin y-koordinaatti</param> 
    /// <param name="palkinkokoX">elamapalkin koko sivusuunnassa</param>  
    /// <param name="palkinkokoY">elamapalkin koko pystysuunnassa</param> 
    /// <param name="elamaPalkinVari">elamapalkin vari</param> 
    /// <param name="elamaPalkkiLayer">kerros taustalla</param> 
    /// <param name="ampumisIntervalli">kuinka nopeasti ampuu ammuksia</param> 
    /// <param name="aluksenLiike">kuinka nopeasti alus liikkuu</param> 
    /// <param name="nollaVektori">aluksen pysaytysvektori</param> 
    private void TeeAlus(double x, double y, double koko, int elamat, string latauskohde, Key vasen, Key oikea, Key ylos, Key alas, Key ammu, int maksimielama, double palkinX, double palkinY, int palkinkokoX, int palkinkokoY, Color elamaPalkinVari, int elamaPalkkiLayer, double ampumisIntervalli, int aluksenLiike, int nollaVektori  )
    {
        Pelaaja alus = new Pelaaja(koko, koko * 1.5, elamat);
        alus.Image = LoadImage(latauskohde);
        alus.X = x;
        alus.Y = y;
        alus.Restitution = 0;
        alus.CanRotate = false;
        alus.IgnoresGravity = true;
        alus.Tag = "hyvis";
        alus.IgnoresExplosions = true;
        Add(alus, 3);
        AluksenTapahtumat(alus, vasen, oikea, ylos, alas, ammu, maksimielama, palkinX, palkinY, palkinkokoX, palkinkokoY, elamaPalkinVari, elamaPalkkiLayer, ampumisIntervalli, aluksenLiike, nollaVektori);

        alus.Destroyed += delegate () 
        {
            ClearAll();
            Level.Background.Image = Level.Background.CreateStars(1000);
            TarinanKuvaus(1200, 400, "PAHIKSET VALLOITTIVAT MAAILMAN, ET ONNISTUNUT PELASTAMAAN GALAKSIA", Color.Transparent, Color.Wheat, 0, 100);
            TarinanKuvaus(1200, 400, "Paina Escape-näppäintä aloittaaksesi alusta", Color.Transparent, Color.Wheat, 0, 50);
            Keyboard.Listen(Key.Escape, ButtonState.Pressed, Begin, "AloitaAlusta");
        };
    }


    /// <summary>
    /// Luodaan alukselle tassa aliohjelmassa tapahtumat esim. tormaykset, ohjaimet yms. 
    /// </summary>
    /// /// <param name="alus">Asetetaan olio, jolle annetaan kaikki parametrit</param> 
    /// <param name="vasen">Asetetaan nappain, jota painettaessa liikkuu vasemmalle</param> 
    /// <param name="oikea">Asetetaan nappain, jota painettaessa liikkuu oikealle</param> 
    /// <param name="ylos">Asetetaan nappain, jota painettaessa liikkuu ylos</param> 
    /// <param name="alas">Asetetaan nappain, jota painettaessa liikkuu alas</param> 
    /// <param name="ammu">Asetetaan nappain, jota painettaessa ampuu</param> 
    /// <param name="maksimielama">Asetetaan alukselle maksimielama</param> 
    /// <param name="palkinX">elamapalkin x-koordinaatti</param> 
    /// <param name="palkinY">elamapalkin y-koordinaatti</param> 
    /// <param name="palkinkokoX">elamapalkin koko sivusuunnassa</param>  
    /// <param name="palkinkokoY">elamapalkin koko pystysuunnassa</param> 
    /// <param name="elamaPalkinVari">elamapalkin vari</param> 
    /// <param name="elamaPalkkiLayer">kerros taustalla</param> 
    /// <param name="ampumisIntervalli">kuinka nopeasti ampuu ammuksia</param> 
    /// <param name="aluksenLiike">kuinka nopeasti alus liikkuu</param> 
    /// <param name="nollaVektori">aluksen pysaytysvektori</param> 
    private void AluksenTapahtumat(Pelaaja alus, Key vasen, Key oikea, Key ylos, Key alas, Key ammu, int maksimielama, double palkinX, double palkinY, int palkinkokoX, int palkinkokoY, Color elamaPalkinVari, int elamaPalkkiLayer, double ampumisIntervalli, int aluksenLiike, int nollaVektori)
    {
        TormaysTapahtumat(alus, "vihuammus", alus, -20, 50, true);
        TormaysTapahtumat(alus, "paaVihuAmpuu", alus, -40, 100, true);
        TormaaOlioon(alus, "elamatahti", +40);
        TormaaOlioon(alus, "pahis", -20);
        TormaaOlioon(alus, "superPahis", -40);
        AsetaOhjaimet(alus, vasen, oikea, ylos, alas, ammu, ampumisIntervalli, aluksenLiike, nollaVektori);
        LuoElamaLaskuri(alus, maksimielama, palkinX, palkinY, palkinkokoX, palkinkokoY, elamaPalkinVari, elamaPalkkiLayer);
    }


    /// <summary>
    /// Luodaan kentalle elamatahti, johon tormatessa saa lisaa elamaa. 
    /// </summary>
    private void LuoElamaTahti()
    {
        PhysicsObject elamatahti = new PhysicsObject(30, 30, Shape.Star);
        elamatahti.X = RandomGen.NextDouble(Level.Left, Level.Right);
        elamatahti.Y = Level.Top;
        Add(elamatahti);
        elamatahti.IgnoresCollisionResponse = true;
        elamatahti.IgnoresExplosions = true;
        elamatahti.Tag = "elamatahti";
        elamatahti.Color = Color.Turquoise;
        elamatahti.Hit(new Vector(0, -600));
        tahtiTimer.Interval = elamatahdenteot[vaikeusTaso];
    }


    /// <summary>
    /// Luon vihuille ajastin, joka tietyin valiajoin laittaa viholliset ampumaan
    /// </summary>
    /// <param name="intervalli">tietty aikavali, jolla kutsutaan ampumis AliOhjelmaa</param> 
    /// <param name="leveys">ammuksen leveys</param>  
    /// <param name="korkeus">ammuksen korkeus</param> 
    /// <param name="x">ammuksen lahtopiste x-koordinaateissa</param> 
    /// <param name="kesto">ammuksen "elinaika" pelikentalla eli se aika, jonka se pysyy pelikentalla aktiivisena, jonka jalkeen se tuhoutuu</param> 
    /// <param name="kantaja">se parametri mille oliolle asetetaan ammus</param> 
    /// <param name="sivulle">impulssivoima X-koordinaateissa</param> 
    /// <param name="ylos">impulssivoima Y-koordinaateissa</param> 
    /// <param name="paikka">ammuksen lahtopiste Y-koordinaateissa</param> 
    /// <param name="vari">ammuksen vari</param> 
    /// <param name="muoto">ammuksen muoto</param> 
    /// <param name="collisionIgnoreGroup">tormayksenhylkiminen tietyille luokille.</param>  
    /// <param name="vihuAseentagi">vihollisen aseelle asetettu tagi</param> 
    /// <param name="eiHuomioiRajahdyksia">totuusarvo, että huomioiko vihun ase rajahdyksia</param> 
    /// <param name="kimmoisuus">vihun ammuksen kimmoisuus</param> 
    /// <param name="kerros">vihun ammuksen kerros kentalla</param> 
    /// <param name="pisteLaskurinMuuttuminen">Kuinka paljon pistelaskuri muuttuu kun vihollinen on tuhottu</param> 
    /// <returns>palauttaa ampumisAjastimen</returns> 
    private Timer AmpumisAjastin(double intervalli, double leveys, double korkeus, double x, double kesto, 
        Pelaaja kantaja, double sivulle, double ylos, double paikka, Color vari, Shape muoto, int collisionIgnoreGroup, 
        string vihuAseentagi, bool eiHuomioiRajahdyksia, int kimmoisuus, int kerros, int pisteLaskurinMuuttuminen)
    {
        Timer ampumisAjastin = new Timer();
        ampumisAjastin.Interval = intervalli;
        ampumisAjastin.Timeout += delegate () 
        {
            VihuAmmu(leveys, korkeus, x, kesto, kantaja, sivulle, ylos, paikka, vari, muoto, 
                     collisionIgnoreGroup, vihuAseentagi, eiHuomioiRajahdyksia, kimmoisuus, kerros);

            Gravity = new Vector(0, RandomGen.NextDouble(-150, 0));
        };
        ampumisAjastin.Start();
        kantaja.Destroyed += delegate () 
        {
            ampumisAjastin.Stop();
            pisteLaskuri.Value += pisteLaskurinMuuttuminen;
        };
        return ampumisAjastin;
    }


    /// <summary>
    /// Luodaan tassa aliohjelmassa tavallinen vihollinen
    /// </summary>
    private void LuoVihu()
    {
        Pelaaja vihuvihu = LuoOlio(30, 60, 30, 100, "NormiVihu", "pahis", RandomGen.NextDouble(Level.Left + 10, Level.Right - 10), RandomGen.NextDouble(Level.Top - 75, Level.Top - 50));
        AmpumisAjastin(1.0, 5, 20, 0, 2, vihuvihu, 0, -400, -40, Color.Red, Shape.Rectangle, 2, "vihuammus", true, 0, 3, +10);
        TormaysTapahtumat(vihuvihu, "ammus", vihuvihu, -10, 50, true);
        LuoAivot(vihuvihu, 200, 3, 300, RandomGen.NextDouble(Level.Left, Level.Right), Level.Top - 75);
        vihuTimer.Interval = vihunteot[vaikeusTaso];
    }


    /// <summary>
    /// Luodaan vahvempi vihollinen
    /// </summary>
    private void LuoPaaVihu()
    {
        Pelaaja paavihu = LuoOlio(200, 250, 300, 1000, "PääVihu", "superPahis", RandomGen.NextDouble(Level.Left + 100, Level.Right - 100), RandomGen.NextDouble(Level.Top - 100, Level.Top));
        AmpumisAjastin(1.0, 5, 26, 0, 2, paavihu, 0, -600, -40, Color.Red, Shape.Rectangle, 2, "paaVihuAmpuu", true, 0, 3, +20);
        TormaysTapahtumat(paavihu, "ammus", paavihu, -10, 50, true);
        LuoAivot(paavihu, 200, 3, 300, RandomGen.NextDouble(Level.Left + 25, Level.Right - 25), Level.Top - 50);
        paavihuTimer.Interval = paavihunteot[vaikeusTaso];
    }


    /// <summary>
    /// Luodaan loppuvihollinen
    /// </summary>
    private void LuoUberVihu()
    {
        Pelaaja uberVihu = LuoOlio(400, 300, 3000, 10000, "UBERVIHU", "uberPahis", 0, Level.Top - 75);
        uberVihu.Shape = Shape.Circle;
        LuoAivot(uberVihu, 300, 7, 250, 0, Level.Top - 75);
        TormaysTapahtumat(uberVihu, "ammus", uberVihu, -10, 50, true);
        AmpumisAjastin(RandomGen.NextDouble(0.5,1.5), 5, 60, 0, 2, uberVihu, 0, -200, -170, RandomGen.NextColor(), Shape.Circle, 2, "vihuammus", true, 0, 3, +1000) ;
        AmpumisAjastin(RandomGen.NextDouble(0.5, 1.5), 5, 60, 70, 2, uberVihu, 0, -600, -170, RandomGen.NextColor(), Shape.Circle, 2, "vihuammus", true, 0, 3, 0);
        AmpumisAjastin(RandomGen.NextDouble(0.5, 1.0), 5, 60, -70, 2, uberVihu, 0, -600, -170, RandomGen.NextColor(), Shape.Circle, 2, "vihuammus", true, 0, 3, 0);
        AmpumisAjastin(RandomGen.NextDouble(0.5, 3.0), 5, 60, 140, 2, uberVihu, 0, -100, -170, RandomGen.NextColor(), Shape.Circle, 2, "vihuammus", true, 0, 3, 0);
        LuoElamaLaskuri(uberVihu, 3000, Level.Right - 225, Level.Top - 75, 200, 25, Color.DarkForestGreen, 2);
        Add(uberVihu);
        MediaPlayer.Play("Avaruus2");
        MediaPlayer.IsRepeating = true;
        uberVihu.Destroyed += VoititPelin;
    }


    /// <summary>
    /// Aliohjelma, joka toimii pohjana vihujen luonnille
    /// </summary>
    /// <param name="kokoX"> koko leveyssuunnassa</param>
    /// <param name="kokoY"> koko pituussuunnassa</param>
    /// <param name="elamat"> elämien maara vihollisilla</param>
    /// <param name="massa"> vihollisten massa</param>
    /// <param name="kuva"> tahan voidaan asettaa contenteista kuva</param>
    /// <param name="tagi"> vihollisen tagi</param>
    /// <param name="x"> vihollisen ilmestyminen x-akselille</param>
    /// <param name="y"> vihollisen ilmestyminen y-akselille</param> 
    /// <returns></returns>
    public Pelaaja LuoOlio(double kokoX, double kokoY, int elamat, int massa, string kuva, string tagi, double x, double y)
    {
        Pelaaja vihollinen = new Pelaaja(kokoX, kokoY , elamat);
        vihollinen.Mass = massa;
        vihollinen.Image = LoadImage(kuva);
        vihollinen.X = x;
        vihollinen.Y = y;
        vihollinen.CanRotate = false;
        vihollinen.IgnoresExplosions = true;
        vihollinen.Tag = tagi;
        vihollinen.CollisionIgnoreGroup = 2;
        Add(vihollinen, 3);
        return vihollinen;
    }


    /// <summary>
    /// Luodaan satunnaisliikkuja viholliselle
    /// </summary>
    /// <param name="aivot"> parametri, jolla voidaan asettaa satunnaisliikkuja tietylle fysiikkaoliolle </param>
    /// <param name="liikkumisNopeus"> int-arvo, jolla asetetaan kuinka nopeasti olio liikkuu </param>
    /// <param name="suunnanmuutos"> parametri, joka katsoo monta sekuntia kuluu ennenkuin vaihtaa suuntaa </param>
    /// <param name="sade"> liikkumisalue </param>
    /// <param name="x"> Liikkumiskoordinaatti leveyssuunnassa </param>
    /// <param name="y"> Liikkumiskoordinaatti pituussuunnassa </param>
    void LuoAivot(Pelaaja aivot, int liikkumisNopeus, int suunnanmuutos, int sade, double x, double y)
    {
        RandomMoverBrain satunnaisAivot = new RandomMoverBrain(liikkumisNopeus);
        satunnaisAivot.ChangeMovementSeconds = suunnanmuutos;
        satunnaisAivot.WanderRadius = sade;
        aivot.Brain = satunnaisAivot;
        satunnaisAivot.WanderPosition = new Vector(x, y);
    }


    /// <summary>
    /// Luodaan liikkuva taustakuva pelille. 
    /// </summary>
    /// <param name="taustanLiikkuminen"> intervalli, joka paattaa monen sekunnin valein siirretaan taustaa eteenpain </param>
    /// <param name="aloitusKuvanIndeksi"> monennesta kuvasta aloitetaan </param>
    /// <param name="kuvienMaara"> monta kuvaa liikkuu pelissa </param>
    /// <param name="leveys"> kuvan leveys </param>
    /// <param name="korkeus"> kuvan pituus </param>
    /// <param name="tahtienMaara"> tahtien maara taustakuvassa </param>
    void LuoTaustakuvat(double taustanLiikkuminen, int aloitusKuvanIndeksi, int kuvienMaara, double leveys, double korkeus, int tahtienMaara)
    {
        List<GameObject> taustakuvat = new List<GameObject>();
        Timer taustaAjastin = new Timer();
        taustaAjastin.Interval = taustanLiikkuminen;
        taustaAjastin.Timeout += delegate() { LiikutaTaustaa(taustakuvat); };
        taustaAjastin.Start();

        for (int i = aloitusKuvanIndeksi; i < kuvienMaara; i++)
        {
            LisaaTaustakuva(leveys, korkeus, tahtienMaara, taustakuvat);
        }
    }


    /// <summary>
    /// Lisätään varsinaiset taustakuvat peliin. 
    /// </summary>
    /// <param name="leveys">taustakuvan leveys </param>
    /// <param name="korkeus">taustakuvan korkeus </param>
    /// <param name="tahtienMaara"> tahtien maara taustakuvassa </param>
    /// <param name="taustakuvat"> lista taustakuvista. </param>
    void LisaaTaustakuva(double leveys, double korkeus, int tahtienMaara, List<GameObject> taustakuvat)
    {
        GameObject taustaAvaruus = new GameObject(leveys, korkeus);
        taustaAvaruus.Image = Level.Background.CreateStars(tahtienMaara);
        taustaAvaruus.X = 0;
        Add(taustaAvaruus, -3);

        if (taustakuvat.Count > 0)
        {
            taustaAvaruus.Top = taustakuvat[taustakuvat.Count - 1].Bottom;
            if (scrollausnopeus >= 0) ekaTaustakuva = taustaAvaruus;
        }
        else
        {
            taustaAvaruus.Top = Level.Top + 50;
            if (scrollausnopeus < 0) ekaTaustakuva = taustaAvaruus;
        }
        taustakuvat.Add(taustaAvaruus);
    }


    /// <summary>
    /// Varsinainen taustakuvan liikuttaja
    /// </summary>
    /// <param name="taustakuvat">lista taustakuvista, ei kuitenkaan tassa alusteta.</param>  
    void LiikutaTaustaa(List<GameObject> taustakuvat)
    {
        foreach (GameObject taustakuva in taustakuvat)
        {
            taustakuva.Y += scrollausnopeus;

            if (scrollausnopeus < 0 && taustakuva.Top < Level.Bottom)
            {
                taustakuva.Bottom = ekaTaustakuva.Top;
                ekaTaustakuva = taustakuva;
            }
            else if (scrollausnopeus > 0 && taustakuva.Bottom > Level.Top)
            {
                taustakuva.Top = ekaTaustakuva.Bottom;
                ekaTaustakuva = taustakuva;
            }
        }
    }


    /// <summary>
    /// Apuohjelma Ammu aliohjelmalle ja näppäinkomennolle. Pysayttaa ampumisen kun vapauttaa ampumisnappaimen
    /// </summary>
    private void LopetaAmpuminen()
    {
        ampumisAjastin.Stop();
    }


    /// <summary>
    /// AliOhjelma joka ampuu kutsuu ammu, aliohjelmaa tietyin valiajoin. Mahdollistaa tietyin valiajoin ampumisen ampumisnappainta pohjassa pitamalla. 
    /// </summary>
    /// <param name="alus">Parametri, joka asetetaan haluamalle pelaajalle. </param> 
    /// <param name="ampumisIntervalli">ampumisintervalli, eli kuinka isoin valiajoin kutsutaan ammu ohjelmaa. </param> 
    private void Ammu(Pelaaja alus, double ampumisIntervalli)
    {
        ampumisAjastin = new Timer();
        ampumisAjastin.Interval = ampumisIntervalli;
        ampumisAjastin.Timeout += delegate ()
        {
            Ammu(5, 25, 2, alus, 0, 2000, 10, 0, true, Color.Turquoise, Shape.Triangle, "ammus");
            SoundEffect ammu = LoadSoundEffect("LaserGunShot");
            ammu.Play();
        };
        ampumisAjastin.Start();
    }


    /// <summary>
    /// Varsinainen aliohjelma, joka luo pelaajalle ammukset. 
    /// </summary>
    /// <param name="leveys">ammuksen leveys</param> 
    /// <param name="korkeus">ammuksen korkeus</param> 
    /// <param name="kesto">kuinka pitkaan ammus kestaa kentalla</param> 
    /// <param name="kantaja">kuka sitä ammusta kantaa, esimerkiksi pelaaja</param> 
    /// <param name="sivulle">sivusuunnan impulssi</param> 
    /// <param name="ylos">pystysuunnan impulssi</param> 
    /// <param name="paikka">ammuksen syntypaikka kentalla pystytasossa</param> 
    /// <param name="x">ammuksen syntypaikka kentalla vaakatasossa</param> 
    /// <param name="hylkiikoRajahdyksiaJaKolhuja">hylkiiko ammus rajahdyksia ja kolhuja</param> 
    /// <param name="vari">ammuksen vari</param> 
    /// <param name="muoto">ammuksen muoto </param> 
    /// <param name="tagi">ammuksen nimi/tagi</param> 
    public PhysicsObject Ammu(double leveys, double korkeus, double kesto, Pelaaja kantaja, double sivulle, double ylos, double paikka, double x, bool hylkiikoRajahdyksiaJaKolhuja, Color vari, Shape muoto, string tagi)
    {
        PhysicsObject aluksenAse = new PhysicsObject(leveys, korkeus, muoto);
        aluksenAse.Position = kantaja.Position + new Vector(x, korkeus + paikka);
        aluksenAse.Color = vari;
        aluksenAse.Hit(new Vector(sivulle, ylos));
        aluksenAse.MaximumLifetime = TimeSpan.FromSeconds(kesto);
        aluksenAse.Tag = tagi;
        aluksenAse.IgnoresExplosions = hylkiikoRajahdyksiaJaKolhuja;
        aluksenAse.IgnoresCollisionResponse = hylkiikoRajahdyksiaJaKolhuja;
        Add(aluksenAse, 3);
        return aluksenAse;
    }


    /// <summary>
    /// Luodaan vihollisen ammukset
    /// </summary>
    /// <param name="leveys">vihollisen ammuksen leveys</param>
    /// <param name="korkeus">vihollisen ammuksen korkeus</param> 
    /// <param name="x">vihollisen ammuksen ilmestyminen sivusuunnassa</param> 
    /// <param name="kesto">vihollisen ammuksen kesto kentalla</param> 
    /// <param name="kantaja">ammuksen kantaja</param> 
    /// <param name="sivulle">impulssi sivusuunnassa</param> 
    /// <param name="ylos">impulssi pystysuunnassa</param> 
    /// <param name="paikka">paikka pystysuunnassa</param> 
    /// <param name="vari">vihun ammuksen vari</param> 
    /// <param name="muoto">ammuksen muoto</param> 
    /// <param name="collisionIgnoreGroup">osumisenhylkijan ryhma</param> 
    /// <param name="vihuAseentagi">vihollisen aseen nimi/tagi</param>  
    /// <param name="hylkiikoRajahdyksiaJaKolhuja">huomioiko rajahdykset ja tormaykset</param> 
    /// <param name="kimmoisuus">kuinka kimmoisa ammus on</param> 
    /// <param name="kerros">kerros kentalla</param> 
    /// <returns>palautta vihollisen aseen/ammuksen.</returns> 
    private PhysicsObject VihuAmmu(double leveys, double korkeus, double x, double kesto, Pelaaja kantaja, double sivulle, double ylos, double paikka, Color vari, Shape muoto,int collisionIgnoreGroup, string vihuAseentagi, bool hylkiikoRajahdyksiaJaKolhuja, int kimmoisuus, int kerros)
    {
        PhysicsObject vihuAse = Ammu(leveys, korkeus, kesto, kantaja, sivulle, ylos, paikka, x, hylkiikoRajahdyksiaJaKolhuja, vari, muoto, vihuAseentagi);
        vihuAse.Tag = vihuAseentagi;
        vihuAse.CollisionIgnoreGroup = collisionIgnoreGroup;
        vihuAse.Restitution = kimmoisuus;
        Add(vihuAse, kerros);
        return vihuAse;
    }


    /// <summary>
    /// Aliohjelma, joka asettaa olioille tormaystapahtumat ammuksiin
    /// </summary>
    /// <param name="kohde"> vastaanottava olio johon osutaan</param> 
    /// <param name="lahto"> olio joka osuu kohteeseen.</param>   
    /// <param name="vastaanottaja"> vastaanottava olio johon osutaan</param> 
    /// <param name="miinusmaara"> kuinka paljon miinustaa elamista</param> 
    /// <param name="rajahdyssade">kuinka iso rajahdyssade</param> 
    /// <param name="ehto">ehto nakyyko rajahdysta vai ei</param> 
    void TormaysTapahtumat(Pelaaja kohde, string lahto, Pelaaja vastaanottaja, int miinusmaara, int rajahdyssade, bool ehto)
    {
        AddCollisionHandler(kohde, lahto, CollisionHandler.AddMeterValue(vastaanottaja.Elamat, miinusmaara));
        AddCollisionHandler(kohde, lahto, CollisionHandler.ExplodeTarget(rajahdyssade, ehto));
    }


    /// <summary>
    /// Aliohjelma, joka asettaa olioiden tormaykset toisiinsa. (huom. ei ammuksiin)
    /// </summary>
    /// <param name="alus">tormaava olio</param> 
    /// <param name="kohde">vastaanottava olio</param> 
    /// <param name="elamaaLisaaTaiVahenna">kuinka paljon elamaa tulee lisaa tai vahenee.</param> 
    void TormaaOlioon(Pelaaja alus, string kohde, int elamaaLisaaTaiVahenna)
    {
        AddCollisionHandler(alus, kohde, CollisionHandler.AddMeterValue(alus.Elamat, elamaaLisaaTaiVahenna));
        AddCollisionHandler(alus, kohde, CollisionHandler.DestroyTarget);
    }


    /// <summary>
    /// Aliohjelma, joka liikuttaa pelaajaa. 
    /// </summary>
    /// <param name="alus">liikuteltava alus</param> 
    /// <param name="x">X suuntaan liikutettava voima</param> 
    /// <param name="y">Y suuntaan liikutettava voima</param> 
    void LiikutaPelaajaa(Pelaaja alus, int x, int y)
    {
        alus.Velocity = new Vector(x, y);   
    }


    /// <summary>
    /// ALiohjelma, joka luo pistelaskurin
    /// </summary>
    /// <param name="pisteLaskurinLahtoPisteet"> pisteet, jotka ovat pelia aloitettaessa. </param> 
    /// <param name="pisteNaytonSijaintiX"> pistenayton sijainti vaakatasossa</param> 
    /// <param name="pisteNaytonSijaintiY"> pistenayton sijainti pystytasossa</param> 
    /// <param name="tekstinVari"> Pistelaskurin tekstin vari</param> 
    /// <param name="taustanVari"> Pistelaskurin taustan vari</param> 
    /// <param name="otsikko"> Pistelaskurin otsikko tai mita siina lukee</param> 
    /// <param name="kerros"> kerros taustalla.</param> 
    void LuoPistelaskuri(int pisteLaskurinLahtoPisteet, double pisteNaytonSijaintiX, double pisteNaytonSijaintiY, Color tekstinVari, Color taustanVari, string otsikko, int kerros)
    {
        pisteLaskuri = new IntMeter(pisteLaskurinLahtoPisteet);
        pisteLaskuri.Changed += PistelaskuriMuuttui;
        Label pisteNaytto = new Label();
        pisteNaytto.X = pisteNaytonSijaintiX;
        pisteNaytto.Y = pisteNaytonSijaintiY;
        pisteNaytto.TextColor = tekstinVari;
        pisteNaytto.Color = taustanVari;
        pisteNaytto.Title = otsikko;
        pisteNaytto.BindTo(pisteLaskuri);
        Add(pisteNaytto, kerros);
    }


    /// <summary>
    /// Aliohjelma, joka suoritetaan kun pistelaskuri muuttuu tietyn rajan yli
    /// </summary>
    /// <param name="oldValue">vanha arvo</param> 
    /// <param name="newValue">uusi arvo, jota verrataan vanhaan ja pisterajoihin.</param> 
    private void PistelaskuriMuuttui(int oldValue, int newValue)
    {
        if(newValue >= pisterajat[vaikeusTaso])
        {
            vaikeusTaso++;
            if (vaikeusTaso >= pisterajat.Length - 1)
            {
                LoppuTaso();
            }
        }
    }


    /// <summary>
    /// Luodaan elamapalkki olioille. 
    /// </summary>
    /// <param name="alus">Maaraa mille oliolle luodaan elamapalkki</param> 
    /// <param name="maksimielama">maksimielama palkille ja oliolle</param> 
    /// <param name="palkinsijaintiX">sijainti X-tasolla</param> 
    /// <param name="palkinsijaintiY">sijainti y-tasolla</param> 
    /// <param name="palkinkokoX">Koko sivusuunnassa</param> 
    /// <param name="palkinkokoY">koko pystysuunnassa</param> 
    /// <param name="elamaPalkinVari">palkinVari</param> 
    /// <param name="elamaPalkkiLayer">kerros taustalla</param> 
    /// <returns></returns>
    private ProgressBar LuoElamaLaskuri(Pelaaja alus, int maksimielama, double palkinsijaintiX, double palkinsijaintiY, int palkinkokoX, int palkinkokoY, Color elamaPalkinVari, int elamaPalkkiLayer)
    {
        IntMeter elamaLaskuri = alus.Elamat;
        elamaLaskuri.MaxValue = maksimielama;
        ProgressBar elamaPalkki = new ProgressBar(palkinkokoX, palkinkokoY);
        elamaPalkki.BarColor = elamaPalkinVari;
        elamaPalkki.BindTo(elamaLaskuri);
        elamaPalkki.X = palkinsijaintiX;
        elamaPalkki.Y = palkinsijaintiY;
        Add(elamaPalkki, elamaPalkkiLayer);
        return elamaPalkki;
    }


    /// <summary>
    /// Aliohjelma, joka asettaa pelaajalle ohjaimet
    /// </summary>
    /// <param name="alus">mille alukselle asetetaan ohjaimet</param> 
    /// <param name="vasen">Asetetaan nappain, jota painettaessa liikkuu vasemmalle</param> 
    /// <param name="oikea">Asetetaan nappain, jota painettaessa liikkuu oikealle</param> 
    /// <param name="ylos">Asetetaan nappain, jota painettaessa liikkuu ylos</param> 
    /// <param name="alas">Asetetaan nappain, jota painettaessa liikkuu alas</param> 
    /// <param name="ammu">Asetetaan nappain, jota painettaessa ampuu</param> 
    /// <param name="ampumisIntervalli"> kuinka suurella väliajalla alus ampuu painettaessa nappainta pohjassa</param> 
    /// <param name="aluksenLiike">kuinka nopeasti alus liikkuu</param> 
    /// <param name="nollaVektori">aluksen pysaytysvektori</param> 
    void AsetaOhjaimet(Pelaaja alus, Key vasen, Key oikea, Key ylos, Key alas, Key ammu, double ampumisIntervalli, int aluksenLiike, int nollaVektori)
    {

        Keyboard.Listen(vasen, ButtonState.Down, LiikutaPelaajaa, "Pelaaja liikkuu vasemmalle", alus, -aluksenLiike, nollaVektori);
        Keyboard.Listen(vasen, ButtonState.Released, LiikutaPelaajaa, "Pelaaja pysähtyy", alus, nollaVektori, nollaVektori);

        Keyboard.Listen(oikea, ButtonState.Down, LiikutaPelaajaa, "Pelaaja liikkuu oikealle", alus, aluksenLiike, nollaVektori);
        Keyboard.Listen(oikea, ButtonState.Released, LiikutaPelaajaa, "Pelaaja pysähtyy", alus, nollaVektori, nollaVektori);

        Keyboard.Listen(ylos, ButtonState.Down, LiikutaPelaajaa, "Pelaaja liikkuu ylos", alus, nollaVektori, aluksenLiike);
        Keyboard.Listen(ylos, ButtonState.Released, LiikutaPelaajaa, "Pelaaja pysähtyy", alus, nollaVektori, nollaVektori);

        Keyboard.Listen(alas, ButtonState.Down, LiikutaPelaajaa, "Pelaaja liikkuu alas", alus, nollaVektori, -aluksenLiike);
        Keyboard.Listen(alas, ButtonState.Released, LiikutaPelaajaa, "Pelaaja pysähtyy", alus, nollaVektori, nollaVektori);

        Keyboard.Listen(ammu, ButtonState.Pressed, Ammu, "ammus", alus, ampumisIntervalli);
        Keyboard.Listen(Key.Space, ButtonState.Released, LopetaAmpuminen, "LopetaAmpuminen");

        Keyboard.Listen(Key.Escape, ButtonState.Pressed, AlkuValikko, "Lopeta peli");
        PhoneBackButton.Listen(ConfirmExit, "Lopeta peli");
    }
}