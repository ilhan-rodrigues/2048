import java.awt.event.KeyEvent;
import java.awt.Font;
import java.util.Random;

public class G2048 {

    // ---------------------------------------------------------------------- //
    // Fonctions utilitaires que vous pourrez utiliser pour implémenter les
    

    static Random rand = new Random ();

    /* La fonction suivante renvoie un entier tiré au hasard entre min et max. */
    public static int randInt (int min, int max) {
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /* Procédure affichant le contenu d'un tableau d'entiers. */
    public static void printIntArray(int[] a) {
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    /* Procédure inversant les éléments d'un tableau

       Exemple : entrée : {1,2,3,4,5}, sortie : {5,4,3,2,1}
     */
    public static void reverse(int[] t) {
        for(int i = 0; i < t.length / 2; i++) {
            int tmp = t[i];
            t[i] = t[t.length - i - 1];
            t[t.length - i - 1] = tmp;
        }
    }

    /* Procédure inversant les lignes et colonnes d'un tableau de tableaux carré
       (les lignes deviennent les colonnes, et les colonnes deviennent les
       lignes).

       Exemple :
       entrée :
         { {0, 1, 2},
           {3, 4, 5},
           {6, 7, 8} }

       sortie :
         { {0, 3, 6},
           {1, 4, 7},
           {2, 5, 8} }
    */
    public static void transpose(int[][] t) {
        for(int i = 0; i < t.length; i++) {
            for(int j = i+1; j < t.length; j++) {
                int tmp = t[i][j];
                t[i][j] = t[j][i];
                t[j][i] = tmp;
            }
        }
    }

    // ---------------------------------------------------------------------- //

    // La grille
    public static int[][] board;

    // Taille de la grille
    public static int boardSize = 4;

    // Coups :
    public static int LEFT = 0;
    public static int RIGHT = 1;
    public static int UP = 2;
    public static int DOWN = 3;

    

    public static void initBoard() {
        board=new int[boardSize][boardSize];
        for(int i=0 ; i<board.length ; i++) {
            for(int j=0 ; j<board[i].length ; j++) {
                board[i][j]=0;
            }
        }
        board[1][0]=2;
        board[3][3]=2;
    }

    public static boolean isBoardWinning() {
        for(int i=0 ; i<board.length ; i++) {
            for(int j=0 ; j<board[i].length ; j++) {
                if(board[i][j]==2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int newSquareValue() {
        if(randInt(0,9)==0) {
            return 4;
        }
        return 2;
    }

    // ...

    

    public static int[] newEmptyRow() {
        int[] t=new int[boardSize];
        for(int i=0 ; i<t.length ; i++) {
            t[i]=0;
        }
        return t;
    }

    public static int[] slideLeftNoMerge(int[] a) {
        int[] t=newEmptyRow();
        int x=0;
        for(int i=0 ; i<a.length ; i++) {
            if(a[i]!=0) {
                t[x]=a[i];
                x++;
            }
        }
        return t;
    }

    public static int[] slideLeftAndMerge(int[] a) {
        int[] t=slideLeftNoMerge(a);
        for(int i=0 ; i<t.length-1 ; i++) {
            if(t[i]==t[i+1]) {
                t[i]=t[i]+t[i+1];
                t[i+1]=0;
                t=slideLeftNoMerge(t);
            }
        }
        return t;
    }

    public static int[] slideRowLeft(int[] a) {
        int[] t=slideLeftAndMerge(a);
        return t;
    }

    // ...

    
    // Implémentez les fonctions demandées ici, et complétez slideBoard().

    public static void slideBoardLeft() {
        for(int i=0 ; i<board.length ; i++) {
            board[i]=slideRowLeft(board[i]);
        }
    }

    public static void slideBoardRight() {
        for(int i=0 ; i<board.length ; i++) {
            reverse(board[i]);
            board[i]=slideRowLeft(board[i]);
            reverse(board[i]);
        }
    }

    public static void slideBoardUp() {
        transpose(board);
        slideBoardLeft();
        transpose(board);
    }

    public static void slideBoardDown() {
        transpose(board);
        slideBoardRight();
        transpose(board);
    }

    public static void slideBoard(int direction) {
        if(direction==LEFT) {
            slideBoardLeft();
        }
        if(direction==RIGHT) {
            slideBoardRight();
        }
        if(direction==UP) {
            slideBoardUp();
        }
        if(direction==DOWN) {
            slideBoardDown();
        }
        return;
    }

   
    // Complétez addSquare

    public static boolean caseVide(int l, int c) {
        if(board[l][c]==0) {
            return true;
        }
        return false;
    }

    public static void addSquare(int value) {
        int l=randInt(0,boardSize-1);
        int c=randInt(0,boardSize-1);
        while(!caseVide(l,c)) {
            l=randInt(0,boardSize-1);
            c=randInt(0,boardSize-1);
        }
        board[l][c]=2;
        return;
    }

    
    // Implémentez ce qui est demandé ici.

    public static boolean isValidMove(int direction) {
        //deux conditions pour chaque direction :
        //1) aucune valeur derrière un 0 en fonction de là où on a bougé
        //2) return false si il n'y a aucune valeur en double cote à cote (sinon ça change bien l'état de la grille)
        return true;
    }

    public static void main(String[] args) {
        // Écrivez vos tests ici
        int[] row={4,2,2,0};
        //printIntArray(slideLeftNoMerge(row));
        //printIntArray(slideLeftAndMerge(row));
        //printIntArray(slideRowLeft(row));


        // ...

        // Exécute la boucle principale du jeu
        // (runGame est définie ci-dessous, mais il n'est pas nécessaire de
        // comprendre ce qu'elle fait en détail).
        runGame();
    }

    // ---------------------------------------------------------------------- //
    // Ci-dessous, fonctions qu'il n'est pas forcément nécessaire de comprendre.
    //

    // move exécute un tour de jeu : si le coup est valide, alors on décale les
    // cases de la grille, et on en ajoute une nouvelle
    public static void move(int direction) {
        if (isValidMove(direction)) {
            slideBoard(direction);
            addSquare(newSquareValue());
        }
    }

    // dessine la grille courante
    public static void drawBoard() {
        StdDraw.clear();
        for(int i = 0; i <= boardSize; i++) {
            StdDraw.line(0, 0.25 * i, 1, 0.25 * i);
            StdDraw.line(0.25 * i, 0, 0.25 * i, 1);
        }

        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if (board[i][j] != 0) {
                    StdDraw.text(j * 0.25 + 0.125,
                                 1 - i * 0.25 - 0.125,
                                 Integer.toString(board[i][j]));
                }
            }
        }
        StdDraw.show();
    }

    // récupère la direction indiquée au clavier
    public static int getDirection() {
        int direction = -1;
        while(direction == -1) {
            if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                direction = LEFT;
            } else if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                direction = RIGHT;
            } else if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                direction = DOWN;
            } else if(StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                direction = UP;
            }
            StdDraw.pause(16);
        }
        StdDraw.clearKeyPressed();
        return direction;
    }

    // boucle principale du jeu : fait jouer des coups jusqu'à ce que la partie
    // soit gagnée
    public static void runGame() {
        Font font = new Font("Sans Serif", Font.PLAIN, 40);
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(font);
        StdDraw.enableDoubleBuffering();

        initBoard();
        drawBoard();

        while(!isBoardWinning()) {
            int direction = getDirection();
            move(direction);
            drawBoard();
        }
    }
}
