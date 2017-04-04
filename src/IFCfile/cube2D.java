package IFCfile;

//
//representation en perspective d'un cube 
//on projete sur le plan z=D

import java.awt.*;

public class cube2D extends Frame {
 // définition de la police d'affichage des textes
 private Font font = new Font("serif", Font.ITALIC + Font.BOLD, 16);

 // les points du cube en 3D centre en 0, de coté 2
 // x du point i: point[i][0]
 // y du point i: point[i][1]
 // z du point i: point[i][2]

 int[][] point   = new int[8][3]; // points du cube dans l'espace 
 int[][] projete = new int[8][2]; // points du cube dans le plan projeté


 // coordonnées min/max du repère du plan de projection dans la fenêtre
 int minx=-300,maxx=300;
 int miny =-300,maxy=300;
 // décalage pour l'afficher le centre du repère au centre de la fenêtre
 int shiftx=-minx,shifty=-miny;

 int D =   40; // distance du point de fuite au plan de projection

 public void initCube()
	/* 
	   initialisation des points du cube
	   Points: 
	   {-1,-1,-1},{-1,-1, 1},{-1, 1, 1},{-1, 1,-1},
	   { 1, 1,-1},{ 1,-1,-1},{ 1,-1, 1},{ 1, 1, 1}
	*/
 {
	point[0][0]=-1; point[0][1]=-1; point[0][2]=-1; //  {-1,-1,-1}
	point[1][0]=-1; point[1][1]=-1; point[1][2]= 1;
	point[2][0]=-1; point[2][1]= 1; point[2][2]= 1;
	point[3][0]=-1; point[3][1]= 1; point[3][2]=-1;
	point[4][0]= 1; point[4][1]= 1; point[4][2]=-1;
	point[5][0]= 1; point[5][1]=-1; point[5][2]=-1;
	point[6][0]= 1; point[6][1]=-1; point[6][2]= 1;
	point[7][0]= 1; point[7][1]= 1; point[7][2]= 1;
 }

 public void etirementCube(int facteur)
 /* 
    modifie les coordonnées des points du cube 
    pour obtenir un étirement de facteur (facteur,facteur,facteur)
 */       
 {

      //  A IMPLEMENTER

 }

 public void rotationCubeOx(double angle /* radian */)
 /* 
    modifie les coordonnées des points du cube 
    pour obtenir une rotation d'angle "angle" (en radian)
    des points du cube autour de l'axe Ox
 */       
 {

       // A IMPLEMENTER

 }

 public void rotationCubeOy(double angle /* radian */)
 /* 
    modifie les coordonnées des points du cube 
    pour obtenir une rotation d'angle "angle" (en radian)
    des points du cube autour de l'axe Oy
 */       
 {

      //  A IMPLEMENTER

 }


 public void rotationCubeOz(double angle /* radian */)
 /* 
    modifie les coordonnées des points du cube 
    pour obtenir une rotation d'angle "angle" (en radian)
    des points du cube autour de l'axe Oz
 */       
 {

      //  A IMPLEMENTER

 }

 public void translationCube(int tx,int ty, int tz)
 /* 
    modifie les coordonnées des points du cube 
    pour obtenir une translation de vecteur (tx,ty,tz)
    des points du cube.
 */       
 {

     //   A IMPLEMENTER

 }


 void calculProjection(int d)
 /*
   calcul les points projetés du cube dans le plan z=d.
   les points projetés seront stockés dans le tableau proj:
   la projection de point[i] sera donc projete[i]
 */
 {

      //  A IMPLEMENTER

 }


 void drawEdge(Graphics g,int i,int j)
 /*
   dessine l'arete du cube entre le sommet i et le sommet j
 */
 {	
	g.drawLine(
		   projete[i%8][0]+shiftx,projete[i%8][1]+shifty,
		   projete[j%8][0]+shiftx,projete[j%8][1]+shifty
		   );
 }

 void drawRepere(Graphics g)
 /*
   dessine le repere du plan de projection
 */
 {
	g.drawLine(shiftx+minx+20,shifty,shiftx+maxx-40,shifty);
	g.drawLine(shiftx,shifty+miny+50,shiftx,shifty+maxy-20);
	g.drawString("0",shiftx-3,shifty-3);
	g.drawString("+x",shiftx+maxx-30,shifty-3);
	g.drawString("+y",shiftx-3,shifty+maxy-10);
	g.drawString("-x",shiftx+minx+10,shifty-3);
	g.drawString("-y",shiftx-3,shifty+miny+40);
 }
 
 public void paint (Graphics g) 
 /* 
    dessine ce qu'il y a à dessiner       
 */
 {
     g.setColor(Color.black);
	g.setFont(font);
	for ( int i=0; i< 8; ++i)
	    {
		Integer j = new Integer(i);
		g.drawString(j.toString(),projete[i][0]+shiftx,projete[i][1]+shifty);
	    }

	drawEdge(g,0,1); drawEdge(g,0,3); drawEdge(g,0,5);
	drawEdge(g,6,1); drawEdge(g,6,5); drawEdge(g,6,7);
	drawEdge(g,2,1); drawEdge(g,2,3); drawEdge(g,2,7);
	drawEdge(g,4,3); drawEdge(g,4,5); drawEdge(g,4,7);

	drawRepere(g);      	
 }

 public static void  main(String[] arg)
 {
	cube2D f = new cube2D();
	f.setSize(f.maxx-f.minx,f.maxy-f.miny);
	f.setVisible(true);
	double a=0.0; // variable utilisee pour faire une rotation
                   // differente toutes les 100 ms
	while (true) // on va faire tourner le cube
     { 
	    try { Thread.sleep(100); } // pause de 100 ms
	    catch(InterruptedException e){ }	
	    f.initCube();             // initialisation du cube
	    f.etirementCube(100);     // etirement du cube pour l'agrandir
	    f.rotationCubeOz(a*3.14/360.0); // rotation du cube autour de l'axe Oz
	    f.translationCube(300,200,200); // translation du cube
	    f.calculProjection(f.D);    // calcul des points projetes
	    f.repaint(); // mise a jour du dessin
	    ++a;
	}
}
}