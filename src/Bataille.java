import extensions.CSVFile;

class Bataille extends Program {

    // VARIABLES GLOBALES ------------------------------------------------------------------------------------------------

    EtatCase[][] mondeBateaux = new EtatCase[][]{ //monde qui ontient les bateaux, le plateau découvert
            {EtatCase.B, EtatCase.B, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.B, EtatCase.B, EtatCase.B},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.B, EtatCase.B, EtatCase.B, EtatCase.B, EtatCase.B},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
    };

    EtatCase[][] mondeJoueur = new EtatCase[][]{ //Monde affiché au joueur
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
            {EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E},
    };

    int vies = 5; //X vies par défaut

    boolean[] questionPosees = { //Liste des idx des questions déjà posées, à FAUX par défaut
            false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false};

    int cptTirs = 0; //nombre de tirs

    CSVFile CSVScores = loadCSV("High Scores.csv");

    String[][] tabScores = CSVToTabString();

    //---------------------------------------------------------------------------------------------------------------------

    void algorithm() {
        menu();
        while (!joueurGagne(mondeJoueur, mondeBateaux) && !joueurPerdu(vies)) {
            clearScreen();
            cursor(0, 0);
            affichagePlateau(EtatCaseToString(mondeJoueur));
            afficherVies(vies);
            println();
            String saisie = saisieJoueur();
            attaqueJoueur(mondeJoueur, mondeBateaux, saisie);
            cptTirs++;
        }
        affichageFinal();
        if (joueurGagne(mondeJoueur, mondeBateaux)) {
            scoreJoueur(cptTirs);
        }
        afficherScores();
        saveCSV(tabScores, "High Scores.csv");
    }

    void scoreJoueur(int cptTirs) {
        boolean newHighScore = false;
        int idxLig = 1;
        String joueur = saisieNomJoueur();
        while (!newHighScore && idxLig < length(tabScores,1)) {
            if (cptTirs < stringToInt(tabScores[idxLig][2])) {
                newHighScore = true;
                for (int i = 5 ; i >= idxLig; i--){
                    tabScores[i][2] = tabScores[i-1][2];
                    tabScores[i][1] = tabScores[i-1][1];
                }
                tabScores[idxLig][2] = Integer.toString(cptTirs);
                tabScores[idxLig][1] = joueur;
                println("\nExeptionnel ! Tu es le numéro " + idxLig + " du podium ! \n");
            } else {
                idxLig++;
            }
        }
    }

    String saisieNomJoueur(){ //Dificile à tester car on demande une saisie
        String saisie = "";
        do {
            println("Veillez entrer votre prénom Capitaine : ");
            saisie = readString();
        } while (length(saisie) > 15);
        return(saisie);
    }

    String[][] CSVToTabString() {
        String[][] tabScores = new String[rowCount(CSVScores)][columnCount(CSVScores)];
        for(int i = 0; i < rowCount(CSVScores); i++) {
            for (int j = 0; j < columnCount(CSVScores); j++) {
                tabScores[i][j] = getCell(CSVScores, i,j);
            }
        }
        return tabScores;
    }

    void afficherScores() {
        String afficherScore = "";
        for (int i = 0; i < length(tabScores, 1); i++){
            print(String.format("%1s", ""));
            afficherScore = tabScores[i][0];
            print(String.format("%-10s", afficherScore));
            for (int j = 1; j < length(tabScores,2); j++) {
                afficherScore = tabScores[i][j] + " ";
                print(String.format("%-17s", afficherScore));
            }
            println();
        }
    }

    void affichagePlateau(String[][] mondeJoueur) {
        print("  ");
        for (int i = 0; i < length(mondeJoueur, 2); i++) {
            print((i + 1) + " ");
        }
        println();
        for (int li = 0; li < length(mondeJoueur, 1); li++) {
            print((char) ('A' + li) + "|");
            for (int col = 0; col < length(mondeJoueur, 2); col++) {
                print(mondeJoueur[li][col]);
            }
            println();
        }
    }

    void afficherVies(int vies) {
        print("\nVies restantes : ");
        for (int i = 0; i < vies; i++) {
            print(ANSI_RED + "\u2665 " + ANSI_RESET);
        }
        println();
    } //affiche le nombre de vies restantes du joueur

    void attaqueJoueur(EtatCase[][] mondeJoueur, EtatCase[][] mondeBateaux, String saisie) {
        int lig = charAt(saisie, 0) - 'A';
        int col = charAt(saisie, 1) - '0' - 1;
        if (attaqueValide(mondeJoueur, mondeBateaux, saisie)) {
            if (poserQuestion()) {
                mondeJoueur[lig][col] = EtatCase.T;
            } else {
                vies -= 1;
            }
        } else if (attaqueDansEau(mondeJoueur, mondeBateaux, saisie)) {
            println("Dans l'eau !");
            mondeJoueur[lig][col] = EtatCase.X;
            delay(2000);
        }
    }

    void affichageFinal() {
        if (joueurPerdu(vies)) {
            println(ANSI_RED + "          _____                    _____                    _____                    _____                           _______                   _____                    _____                    _____          \n" +
                    "         /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                         /::\\    \\                 /\\    \\                  /\\    \\                  /\\    \\         \n" +
                    "        /::\\    \\                /::\\    \\                /::\\____\\                /::\\    \\                       /::::\\    \\               /::\\____\\                /::\\    \\                /::\\    \\        \n" +
                    "       /::::\\    \\              /::::\\    \\              /::::|   |               /::::\\    \\                     /::::::\\    \\             /:::/    /               /::::\\    \\              /::::\\    \\       \n" +
                    "      /::::::\\    \\            /::::::\\    \\            /:::::|   |              /::::::\\    \\                   /::::::::\\    \\           /:::/    /               /::::::\\    \\            /::::::\\    \\      \n" +
                    "     /:::/\\:::\\    \\          /:::/\\:::\\    \\          /::::::|   |             /:::/\\:::\\    \\                 /:::/~~\\:::\\    \\         /:::/    /               /:::/\\:::\\    \\          /:::/\\:::\\    \\     \n" +
                    "    /:::/  \\:::\\    \\        /:::/__\\:::\\    \\        /:::/|::|   |            /:::/__\\:::\\    \\               /:::/    \\:::\\    \\       /:::/____/               /:::/__\\:::\\    \\        /:::/__\\:::\\    \\    \n" +
                    "   /:::/    \\:::\\    \\      /::::\\   \\:::\\    \\      /:::/ |::|   |           /::::\\   \\:::\\    \\             /:::/    / \\:::\\    \\      |::|    |               /::::\\   \\:::\\    \\      /::::\\   \\:::\\    \\   \n" +
                    "  /:::/    / \\:::\\    \\    /::::::\\   \\:::\\    \\    /:::/  |::|___|______    /::::::\\   \\:::\\    \\           /:::/____/   \\:::\\____\\     |::|    |     _____    /::::::\\   \\:::\\    \\    /::::::\\   \\:::\\    \\  \n" +
                    " /:::/    /   \\:::\\ ___\\  /:::/\\:::\\   \\:::\\    \\  /:::/   |::::::::\\    \\  /:::/\\:::\\   \\:::\\    \\         |:::|    |     |:::|    |    |::|    |    /\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/\\:::\\   \\:::\\____\\ \n" +
                    "/:::/____/  ___\\:::|    |/:::/  \\:::\\   \\:::\\____\\/:::/    |:::::::::\\____\\/:::/__\\:::\\   \\:::\\____\\        |:::|____|     |:::|    |    |::|    |   /::\\____\\/:::/__\\:::\\   \\:::\\____\\/:::/  \\:::\\   \\:::|    |\n" +
                    "\\:::\\    \\ /\\  /:::|____|\\::/    \\:::\\  /:::/    /\\::/    / ~~~~~/:::/    /\\:::\\   \\:::\\   \\::/    /         \\:::\\    \\   /:::/    /     |::|    |  /:::/    /\\:::\\   \\:::\\   \\::/    /\\::/   |::::\\  /:::|____|\n" +
                    " \\:::\\    /::\\ \\::/    /  \\/____/ \\:::\\/:::/    /  \\/____/      /:::/    /  \\:::\\   \\:::\\   \\/____/           \\:::\\    \\ /:::/    /      |::|    | /:::/    /  \\:::\\   \\:::\\   \\/____/  \\/____|:::::\\/:::/    / \n" +
                    "  \\:::\\   \\:::\\ \\/____/            \\::::::/    /               /:::/    /    \\:::\\   \\:::\\    \\                \\:::\\    /:::/    /       |::|____|/:::/    /    \\:::\\   \\:::\\    \\            |:::::::::/    /  \n" +
                    "   \\:::\\   \\:::\\____\\               \\::::/    /               /:::/    /      \\:::\\   \\:::\\____\\                \\:::\\__/:::/    /        |:::::::::::/    /      \\:::\\   \\:::\\____\\           |::|\\::::/    /   \n" +
                    "    \\:::\\  /:::/    /               /:::/    /               /:::/    /        \\:::\\   \\::/    /                 \\::::::::/    /         \\::::::::::/____/        \\:::\\   \\::/    /           |::| \\::/____/    \n" +
                    "     \\:::\\/:::/    /               /:::/    /               /:::/    /          \\:::\\   \\/____/                   \\::::::/    /           ~~~~~~~~~~               \\:::\\   \\/____/            |::|  ~|          \n" +
                    "      \\::::::/    /               /:::/    /               /:::/    /            \\:::\\    \\                        \\::::/    /                                      \\:::\\    \\                |::|   |          \n" +
                    "       \\::::/    /               /:::/    /               /:::/    /              \\:::\\____\\                        \\::/____/                                        \\:::\\____\\               \\::|   |          \n" +
                    "        \\::/____/                \\::/    /                \\::/    /                \\::/    /                         ~~                                               \\::/    /                \\:|   |          \n" +
                    "                                  \\/____/                  \\/____/                  \\/____/                                                                            \\/____/                  \\|___|          \n" +
                    "                                                                                                                                                                                                                \n" +
                    "\n" + ANSI_RESET);
        } else {
            println("         _________ _______ _________ _______ _________ _______  _______ \n" +
                    "|\\     /|\\__   __/(  ____ \\\\__   __/(  ___  )\\__   __/(  ____ )(  ____ \\\n" +
                    "| )   ( |   ) (   | (    \\/   ) (   | (   ) |   ) (   | (    )|| (    \\/\n" +
                    "| |   | |   | |   | |         | |   | |   | |   | |   | (____)|| (__    \n" +
                    "( (   ) )   | |   | |         | |   | |   | |   | |   |     __)|  __)   \n" +
                    " \\ \\_/ /    | |   | |         | |   | |   | |   | |   | (\\ (   | (      \n" +
                    "  \\   /  ___) (___| (____/\\   | |   | (___) |___) (___| ) \\ \\__| (____/\\\n" +
                    "   \\_/   \\_______/(_______/   )_(   (_______)\\_______/|/   \\__/(_______/\n" +
                    "                                                                        \n" +
                    "\n");
        }
    }

    void menu() {
        println(ANSI_BLUE + "                                                                                                                                                                                                                  \n" +
                " ____             ______          ____        _____    _____   ______    ____  _____   ______         _____                _____          ____    _________________  _________________  ____             ______   \n" +
                "|    |        ___|\\     \\    ____|\\   \\   ___|\\    \\  |\\    \\ |\\     \\  |    ||\\    \\ |\\     \\    ___|\\    \\          ___|\\     \\    ____|\\   \\  /                 \\/                 \\|    |        ___|\\     \\  \n" +
                "|    |       |     \\     \\  /    /\\    \\ |    |\\    \\  \\\\    \\| \\     \\ |    | \\\\    \\| \\     \\  /    /\\    \\        |    |\\     \\  /    /\\    \\ \\______     ______/\\______     ______/|    |       |     \\     \\ \n" +
                "|    |       |     ,_____/||    |  |    ||    | |    |  \\|    \\  \\     ||    |  \\|    \\  \\     ||    |  |____|       |    | |     ||    |  |    |   \\( /    /  )/      \\( /    /  )/   |    |       |     ,_____/|\n" +
                "|    |  ____ |     \\--'\\_|/|    |__|    ||    |/____/    |     \\  |    ||    |   |     \\  |    ||    |    ____       |    | /_ _ / |    |__|    |    ' |   |   '        ' |   |   '    |    |  ____ |     \\--'\\_|/\n" +
                "|    | |    ||     /___/|  |    .--.    ||    |\\    \\    |      \\ |    ||    |   |      \\ |    ||    |   |    |      |    |\\    \\  |    .--.    |      |   |              |   |        |    | |    ||     /___/|  \n" +
                "|    | |    ||     \\____|\\ |    |  |    ||    | |    |   |    |\\ \\|    ||    |   |    |\\ \\|    ||    |   |_,  |      |    | |    | |    |  |    |     /   //             /   //        |    | |    ||     \\____|\\ \n" +
                "|____|/____/||____ '     /||____|  |____||____| |____|   |____||\\_____/||____|   |____||\\_____/||\\ ___\\___/  /|      |____|/____/| |____|  |____|    /___//             /___//         |____|/____/||____ '     /|\n" +
                "|    |     |||    /_____/ ||    |  |    ||    | |    |   |    |/ \\|   |||    |   |    |/ \\|   ||| |   /____ / |      |    /     || |    |  |    |   |`   |             |`   |          |    |     |||    /_____/ |\n" +
                "|____|_____|/|____|     | /|____|  |____||____| |____|   |____|   |___|/|____|   |____|   |___|/ \\|___|    | /       |____|_____|/ |____|  |____|   |____|             |____|          |____|_____|/|____|     | /\n" +
                "  \\(    )/     \\( |_____|/   \\(      )/    \\(     )/       \\(       )/    \\(       \\(       )/     \\( |____|/          \\(    )/      \\(      )/       \\(                 \\(              \\(    )/     \\( |_____|/ \n" +
                "   '    '       '    )/       '      '      '     '         '       '      '        '       '       '   )/              '    '        '      '         '                  '               '    '       '    )/    \n" +
                "                     '                                                                                  '                                                                                                   '     \n" +
                "\n" + ANSI_RESET);
        println(ANSI_RED +"[1]"+ ANSI_RESET + " Jouer");
        println(ANSI_RED +"[2]"+ ANSI_RESET + " Règles");
        print("Veuillez saisir votre choix : ");
        if (equals(saisieMenu(), "2")) {
            clearScreen();
            cursor(0, 0);
            regles();
        }
    }

    void regles() {
        CSVFile regles = loadCSV("Regles.csv");
        for (int i = 0; i < rowCount(regles); i++) {
            println(getCell(regles, i, 0));
        }
        String saisieStart = readString();
        while (!commencer(saisieStart)) {
            println("\nAïe ! Ce n'est pas la bonne combinaison !");
            saisieStart = readString();
        }
        println("\nBien joué capitaine ! Que la partie... COMMENCE :)");
        delay(2000);
        clearScreen();
        cursor(0, 0);
    }

    void afficherQuestion(int idxQ) {
        CSVFile questions = loadCSV("Questions.csv");
        for (int i = 0; i < (columnCount(questions, idxQ) -1); i++) {
            println(getCell(questions, idxQ, i));
        }
    }

    String[][] EtatCaseToString(EtatCase[][] mondeJoueur) { //plateau en String pour permettre un affichage plus lisible avec des espaces
        String[][] grille = new String[9][9];
        for (int li = 0; li < length(mondeJoueur); li++) {
            for (int co = 0; co < length(mondeJoueur); co++) {
                if (mondeJoueur[li][co] == EtatCase.E) {
                    grille[li][co] = ANSI_BLUE + ". " + ANSI_RESET;
                } else if (mondeJoueur[li][co] == EtatCase.T) {
                    grille[li][co] = ANSI_YELLOW + "O " + ANSI_RESET;
                } else {
                    grille[li][co] = ANSI_BOLD + "X " + ANSI_RESET;
                }
            }
        }
        return grille;
    }

    boolean joueurGagne(EtatCase[][] plateauJoueur, EtatCase[][] plateauBateaux) {
        for (int lig = 0; lig < length(plateauJoueur, 1); lig++) {
            for (int col = 0; col < length(plateauJoueur, 2); col++) {
                if (plateauBateaux[lig][col] == EtatCase.B && plateauJoueur[lig][col] != EtatCase.T) {
                    return false;
                }
            }
        }
        return true;
    } //VRAI si le joueur gagne

    boolean joueurPerdu(int vies) {
        return vies < 1;
    } // VRAI si le joueur perd (n'a plus aucune vies)

    String saisieJoueur() {
        String saisie = "";
        print("Entrez une coordonnée entre A1 et I9 : ");
        do {
            saisie = readString();
            if (!saisieCoordValide(saisie)) {
                print("Entre A1 et I9 on a dit ! Allez recommence : ");
            }
        } while (!saisieCoordValide(saisie));
        return toUpperCase(saisie);
    } //Pas de test pour cette fonction, elle est vérifiée par la saisieValide en dessous

    boolean saisieCoordValide(String saisie) {
        if (length(saisie) == 2) {
            saisie = toUpperCase(saisie); //On met la saisie en majuscules pour éviter un crash suite à la casse saisie
            char ligne = charAt(saisie, 0);
            char colonne = charAt(saisie, 1);
            return ligne >= 'A' && ligne <= 'I' && colonne >= '1' && colonne <= '9';
        } else {
            return false;
        }
    } //On vérifie que la saisie du joueur est comprise entre A1 et J9

    boolean attaqueValide(EtatCase[][] mondeJoueur, EtatCase[][] mondeBateaux, String saisie) {
        int lig = charAt(saisie, 0) - 'A';
        int col = charAt(saisie, 1) - '0' - 1;
        //Le cas où l'on pose une question
        return (mondeJoueur[lig][col] != EtatCase.X && mondeJoueur[lig][col] != EtatCase.T
                && mondeBateaux[lig][col] == EtatCase.B && !attaqueDansEau(mondeJoueur, mondeBateaux, saisie));
    }

    boolean attaqueDansEau(EtatCase[][] mondeJoueur, EtatCase[][] mondeBateaux, String saisie) {
        int lig = charAt(saisie, 0) - 'A';
        int col = charAt(saisie, 1) - '0' - 1;
        //Le cas où l'attaque est dans l'eau et la case pas déjà attaquée
        return mondeJoueur[lig][col] == EtatCase.E && mondeBateaux[lig][col] == EtatCase.E
                && !attaqueValide(mondeJoueur, mondeBateaux, saisie);
    }

    boolean poserQuestion() {
        CSVFile questions = loadCSV("Questions.csv");
        int idxQ;
        println("Touché ! Pour valider l'attaque, répond correctement à cette question : \n");
        do {
            idxQ = (int) (random() * (rowCount(questions)));
        } while (questionPosees[idxQ]);
        afficherQuestion(idxQ);
        if (equals(saisieReponse(), getCell(questions, idxQ, 5))) {
            println("\nBonne réponse!");
            questionPosees[idxQ] = true;
            delay(2000);
            clearScreen();
            cursor(0,0);
            return true;
        } else {
            println("\nDommage... Tu perds une vie!");
            questionPosees[idxQ] = true;
            delay(2000);
            clearScreen();
            cursor(0, 0);
            return false;
        }
    }

    String saisieReponse() {
        String saisie = "";
        print("\nVeuillez saisir une réponse entre a et d : ");
        do {
            saisie = readString();
            if (!saisieReponseValide(saisie)) {
                print("Une réponse valide on a dit, entre A et D ! Allez recommence : ");
            }
        } while (!saisieReponseValide(saisie));
        return toUpperCase(saisie);
    } //Pas de test pour cette fonction, elle est vérifiée par la saisieReponseValide en bas

    boolean saisieReponseValide(String saisieReponse) {
        if (length(saisieReponse) == 1) {
            saisieReponse = toUpperCase(saisieReponse); //On met la saisie en majuscules pour éviter un crash suite à la casse saisie
            char reponse = charAt(saisieReponse, 0);
            return (reponse >= 'A' && reponse <= 'D');
        } else {
            return false;
        }
    } //On vérifie que la saisie du joueur est comprise entre A et D

    String saisieMenu() {
        String saisie = "";
        do {
            saisie = readString();
            if (!saisieMenuValide(saisie)) {
                print("Entrez seulement 1 ou 2 pour valider votre choix : ");
            }
        } while (!saisieMenuValide(saisie));
        return saisie;
    } //Pas de test pour cette fonction, elle est vérifiée par la saisieMenuValide en bas

    boolean saisieMenuValide(String saisie) {
        return (equals(saisie, "1")) || (equals(saisie, "2"));
    }//On vérifie que la saisie du joueur est 1 ou 2

    boolean commencer(String saisieStart) {
        return equals(saisieStart, "BCDQSSTTU");
    } //Vérifier si la bonne combinaison est saisie

    void testJoueurPerdu() {
        assertFalse(joueurPerdu(2));
        assertTrue(joueurPerdu(0));
        assertFalse(joueurPerdu(1));
    }

    void testSaisieCoordValide() {
        assertTrue(saisieCoordValide("A1"));
        assertTrue(saisieCoordValide("A9"));
        assertTrue(saisieCoordValide("A1"));
        assertTrue(saisieCoordValide("b5"));
        assertFalse(saisieCoordValide("A0"));
        assertFalse(saisieCoordValide("K9"));
        assertFalse(saisieCoordValide("A10"));
        assertFalse(saisieCoordValide("f"));
        assertFalse(saisieCoordValide("zefvzee"));
    }

    void testAttaqueValide() {
        EtatCase[][] mondeJoueur = new EtatCase[][]{{EtatCase.E, EtatCase.E, EtatCase.T, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}};
        EtatCase[][] mondeBateaux = new EtatCase[][]{{EtatCase.B, EtatCase.B, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}};
        assertTrue(attaqueValide(mondeJoueur, mondeBateaux, "A2")); //VRAI car on vise une case eau où se trouve un bateau non-touché
        assertFalse(attaqueValide(mondeJoueur, mondeBateaux, "A4")); //FAUX car on vise une case eau sans bateau
        assertFalse(attaqueValide(mondeJoueur, mondeBateaux, "A3")); //FAUX car on vise une case déjà touchée
    }

    void testAttaqueDansEau() {
        EtatCase[][] mondeJoueur = new EtatCase[][]{{EtatCase.E, EtatCase.E, EtatCase.T, EtatCase.E, EtatCase.X, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}};
        EtatCase[][] mondeBateaux = new EtatCase[][]{{EtatCase.B, EtatCase.B, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}};
        assertTrue(attaqueDansEau(mondeJoueur, mondeBateaux, "A4")); //VRAI car on vise une case eau pas encore attaquée et qui n'est pas un bateau
        assertFalse(attaqueDansEau(mondeJoueur, mondeBateaux, "A2")); //FAUX car on vise une case eau avec bateau
        assertFalse(attaqueDansEau(mondeJoueur, mondeBateaux, "A5")); //FAUX car on vise une case eau déjà touchée
    }

    void testSaisieReponseValide() {
        assertTrue(saisieReponseValide("a"));
        assertTrue(saisieReponseValide("B"));
        assertTrue(saisieReponseValide("C"));
        assertTrue(saisieReponseValide("D"));
        assertTrue(saisieReponseValide("d"));
        assertFalse(saisieReponseValide("A0"));
        assertFalse(saisieReponseValide("e"));
        assertFalse(saisieReponseValide("abcd"));
        assertFalse(saisieReponseValide("AZERTY"));
        assertFalse(saisieReponseValide("zefvzee"));
    }

    void testSaisieMenuValide() {
        assertTrue(saisieMenuValide("1"));
        assertTrue(saisieMenuValide("2"));
        assertFalse(saisieMenuValide("3"));
        assertFalse(saisieMenuValide("v"));
        assertFalse(saisieMenuValide("^"));
    }

    void testSaisieCombinaison() {
        assertFalse(commencer("majong"));
        assertFalse(commencer("cdpqssttu"));
        assertFalse(commencer("badminton"));
        assertFalse(commencer(""));
        assertTrue(commencer("BCDQSSTTU"));
    }

    void testJoueurGagne() {
        assertFalse(joueurGagne(
                new EtatCase[][]{{EtatCase.T, EtatCase.T, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}},
                new EtatCase[][]{{EtatCase.B, EtatCase.B, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}}));
        assertTrue(joueurGagne(
                new EtatCase[][]{{EtatCase.T, EtatCase.T, EtatCase.T, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}},
                new EtatCase[][]{{EtatCase.B, EtatCase.B, EtatCase.B, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E, EtatCase.E}}));
    }
}
