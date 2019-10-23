package utt.if26.bardcamp.bdd;

import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.List;


public class test {

    public static final String create = "INSERT INTO `User` (`idu`,`prenom`,`nom`,`pseudo`,`date_de_naissance`,discographie) VALUES (1,\"Teagan\",\"Carson\",\"Nunc@seddolorFusce.org\",\"15/02/19\",70),(2,\"Shelly\",\"Harrison\",\"lorem@eu.net\",\"26/04/19\",55),(3,\"Forrest\",\"Jenkins\",\"Ut@vitaeerat.co.uk\",\"22/03/19\",35),(4,\"Amethyst\",\"Richardson\",\"adipiscing.elit@tellussem.co.uk\",\"21/05/20\",82),(5,\"Cody\",\"Hill\",\"mollis@adipiscingMauris.net\",\"13/08/20\",93),(6,\"Alfonso\",\"Garza\",\"at.fringilla.purus@id.org\",\"01/07/19\",50),(7,\"Evan\",\"Ware\",\"egestas.Fusce.aliquet@dictumeueleifend.ca\",\"09/06/19\",75),(8,\"Upton\",\"Petty\",\"sociis.natoque@lectussitamet.com\",\"15/10/19\",53),(9,\"Garrett\",\"Cummings\",\"Donec.tempus.lorem@commodoauctor.org\",\"25/05/20\",80),(10,\"Merritt\",\"Richardson\",\"facilisis.vitae@nislNullaeu.co.uk\",\"22/03/19\",71);";

    public test(){
        new test((Context) null);
    }


    public test(Context context){
        //SQLiteDatabase db ;

        bddUser test = new bddUser(context , "User", null, 1);
        bddMusique mm = new bddMusique(context, "Musique", null, 1);
        bddEcoute ecoute = new bddEcoute(context, "Ecoute", null, 1);
        test.open();
        mm.open();
        ecoute.open();

        //INSERT

        test.insert(1,"Nora","Vargas","cursus@nullaatsem.org","04/03/20",26);
        test.insert(2,"Geoffrey","Levy","Curae.Phasellus.ornare@congueturpis.edu","21/03/19",56);
        test.insert(3,"Kermit","Madden","Etiam.laoreet@per.edu","22/12/18",82);
        test.insert(4,"Richard","Zamora","diam.luctus.lobortis@sem.net","28/07/20",16);
        test.insert(5,"Cassidy","Booker","cursus.et@In.ca","22/10/19",72);
        test.insert(6,"Upton","Cochran","ultricies@ut.co.uk","16/02/19",21);
        test.insert(7,"Slade","Estrada","magna.tellus@ametdapibus.edu","26/02/20",87);
        test.insert(8,"Cailin","Harding","imperdiet.ullamcorper@sociisnatoquepenatibus.edu","24/06/20",85);
        test.insert(9,"Buckminster","England","adipiscing@blanditNam.ca","28/02/19",20);
        test.insert(10,"Fallon","Reyes","a.odio@senectus.org","31/12/18",19);
        test.insert(11,"Nevada","Lang","egestas@dolor.ca","30/06/20",40);

        mm.insert(1,"Non Arcu PC",           3,"/data/dot/noPicHere", "20-01-17","/data/1");
        mm.insert(2,"Quis Associates",       2,"/2/data","19-01-19","/data/dot/noMusicHere");
        mm.insert(3,"Egestas Institute",     8,"/2/1","19-12-21","/2, /myMusic");
        mm.insert(4,"Accumsan Associates",   9,"/data/2","19-10-20","/music/dot/noMusicHere");
        mm.insert(5,"Faucibus Ut LLC",       4,"/data/2","19-03-29","/myMusic/1");
        mm.insert(6,"Ultrices  Industries",  9,"/pic/data","20-06-03","/2/dot/noMusicHere");
        mm.insert(7,"Vitae Inc.",            5,"/pic/1","20-06-18","/2/music");
        mm.insert(8,"Magna Institute",       7,"/myPicture/2","19-08-23","/music/data");
        mm.insert(9,"Interdum C LLP",        6,"/dot/noPicHere/2","20-08-10","/dot/noMusicHere/2");
        mm.insert(10,"Tellus Industries",    7,"/1/2","19-01-17","/myMusic/1");

        ecoute.insert(3,2);
        ecoute.insert(1,7);
        ecoute.insert(10,9);
        ecoute.insert(9,4);
        ecoute.insert(1,9);
        ecoute.insert(9,5);
        ecoute.insert(10,2);
        ecoute.insert(8,1);
        ecoute.insert(8,3);
        ecoute.insert(3,7);

        //DELETE
        test.delete("idu", 5);

        //UPDATE
        test.update("pseudo","Pustule", "idu"+ " =?", "11" );

        //SELECT
        List itemIds = test.select( test.getProjection(), test.getKey() + " >?", new String[] {String.valueOf(5)});

        //VOIR RESULT SELECT
        Iterator iterator = itemIds.iterator();
        while(iterator.hasNext()){
            Log.d("STATE", "### User : "+iterator.next().toString()+" ################################################################");
        }
        //Idem pour Musique
        List itemIds2 = mm.select(mm.getProjection(), mm.getKey() + ">?", new String[] {String.valueOf(0)});
        Iterator iterator2 = itemIds2.iterator();
        while(iterator2.hasNext()){
            Log.d("STATE", "### Musique : "+iterator2.next().toString()+" ################################################################");
        }
        //idem pour Ecoute
        List itemIds3 = ecoute.select(ecoute.getProjection(), ecoute.getKey() + ">?", new String[] {String.valueOf(0)});
        Iterator iterator3 = itemIds3.iterator();
        while(iterator3.hasNext()){
            Log.d("STATE", "### ECOUTE : "+iterator3.next().toString()+" ################################################################");
        }

        //sélectionne les musiques où l'auteur a un id supérieur à 5
        //SELECT m.* FROM User u, Musqique m, Ecoute e WHERE u.idu >5 AND u.idu = e.idu AND e.idm = m.idm
        Iterator iterator4 =ecoute.crossSelect(itemIds,"idu", mm,"idu_artiste").iterator();
        while(iterator4.hasNext()){
            Log.d("STATE", "### cross "+iterator4.next()+" ################################################################");
        }

        Log.d("STATE", "bye ### #########################################");
        //test.close();//pour sauvegarder les changements
    }


}
