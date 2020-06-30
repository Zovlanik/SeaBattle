
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    //===============Основной сценарий:=================================================================================

    public static void main(String[] args) {

        printField(myField, enemyField);
        putShipManual();

        System.out.println("Корабли игрока установлены.");
        System.out.println("Инициализируем установку кораблей противника...");

        compPutShip();
        System.out.println("Корабли противника установлены.");

        System.out.println("\n\n\n\n\n\nПереходим в режим стрельбы. Игрок делает первый ход, указывая координаты поля для атаки по маске <буква><цифра>");

        while(!STOPGAME) {
            shoot();
            if (whoWin(compShips)) {
                STOPGAME = true;
                System.out.println("\n==============================================" +
                                   "\n=== ПОЗДРАВЛЯЕМ! ВЫ ПОБЕДИЛИ!!! ==============" +
                                   "\n==============================================");
                break;
            }
            compShoot();
            if (whoWin(playerShips)){
                STOPGAME = true;
                System.out.println("\n==============================================" +
                                   "\n=== К СОЖАЛЕНИЮ, ПОБЕДИЛ КОМПЬЮТЕР ===========" +
                                   "\n==============================================");
                break;
            }
            update(myField,myFieldShow);
            updateHideShips(enemyField,enemyFieldShow);
            printField(myFieldShow,enemyFieldShow);
        }

        //последний раз обновляем поле и показываем его:
        update(myField,myFieldShow);
        updateHideShips(enemyField,enemyFieldShow);
        printField(myFieldShow,enemyFieldShow);
    }

    //===============Вспомогательные методы:============================================================================
    static boolean STOPGAME = false;
    final static char SHIP = '▄';//▄█
    final static char MISS = '•';
    final static char HIT = 'X';
    static char[][] myField = {
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
    };
    static char[][] myFieldShow = {
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
    };
    static char[][] enemyField = {
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
    };
    static char[][] enemyFieldShow = {
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'},
    };
    static int playerShips = 20;
    static int compShips = 20;
    static Map<String, Integer> maskRow = new HashMap<>();
    static Map<String, Integer> maskColumn = new HashMap<>();
    static {
        maskRow.put("А", 0);
        maskRow.put("Б", 1);
        maskRow.put("В", 2);
        maskRow.put("Г", 3);
        maskRow.put("Д", 4);
        maskRow.put("Е", 5);
        maskRow.put("Ж", 6);
        maskRow.put("З", 7);
        maskRow.put("И", 8);
        maskRow.put("К", 9);

        maskColumn.put("1", 0);
        maskColumn.put("2", 1);
        maskColumn.put("3", 2);
        maskColumn.put("4", 3);
        maskColumn.put("5", 4);
        maskColumn.put("6", 5);
        maskColumn.put("7", 6);
        maskColumn.put("8", 7);
        maskColumn.put("9", 8);
        maskColumn.put("10", 9);
    }

    static void printField(char[][] field, char[][] enemyField) {
        System.out.println("     Поле игрока:          ||            Поле компьютера:");
        System.out.println("   A Б В Г Д Е Ж З И К     ||          A Б В Г Д Е Ж З И К");

        for (int i = 1; i < 11; i++) {
            System.out.print((i >= 10 ? "" : " ") + (i) + " ");
            for (int j = 1; j < 11; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.print("    ||       ");
            System.out.print((i >= 10 ? "" : " ") + (i) + " ");
            for (int j = 1; j < 11; j++) {
                System.out.print(enemyField[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void update(char[][] fieldInfo, char[][] fieldShow){
        for(int i = 0; i < fieldInfo.length;i++){
            for(int j = 0; j < fieldInfo[0].length; j ++){
                if (fieldInfo[i][j] == '1') fieldShow[i][j] = SHIP;
                if (fieldInfo[i][j] == 'X') fieldShow[i][j] = HIT;
                if (fieldInfo[i][j] == MISS) fieldShow[i][j] = MISS;

            }
        }
    }

    static void updateHideShips(char[][] fieldInfo, char[][] fieldShow){
        for(int i = 0; i < fieldInfo.length;i++){
            for(int j = 0; j < fieldInfo[0].length; j ++){
                //if (fieldInfo[i][j] == '1') fieldShow[i][j] = SHIP;
                if (fieldInfo[i][j] == 'X') fieldShow[i][j] = HIT;
                if (fieldInfo[i][j] == MISS) fieldShow[i][j] = MISS;

            }
        }
    }

    public static void putShipManual() {
        System.out.println("Режим установки кораблей...\nВыберите ориентацию корабля: \n 1 = вертикально \n 2 = горизонтально\n");
        System.out.println("Выберите размер корабля: \n 1 = однопалубник \n 2 = двухпалубник\n 3 = трехпалубник \n 4 = четырехпалубник\n");
        System.out.println("Для упрощения ввода, реализован метод определения ориентации и начального положения корабля.\n" +
                "Вводить команды нужно через пробел, по одной.\n" +
                "Например: 1 2 А5 = такая команда поставит вертикально двухпалубник на точки А5 и А6\n" +
                "2 4 Б6 = такая команда поставит горизонтально четырёхпалубник на точки Б6 В6 Г6 и Д6");

        //Задаём количество доступных для размещения кораблей игроком
        int fourDeck = 1;
        int threeDeck = 2;
        int twoDeck = 3;
        int oneDeck = 4;

        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String temp = scan.nextLine().toUpperCase();
            int orientation, size;
            String  key, key2;
            boolean postShip; //инициализируется как false

            //отладка
            if(temp.equals("СТОП")) break; //выход по требованию
            //конец отладки

            try {
                orientation = Integer.parseInt(temp.substring(0,1)); // Ориентация. Должна быть либо 1 (вертикально), либо 2 (горизонтально)
                size = Integer.parseInt(temp.substring(2,3)); // Размер корабля. Либо 1, либо 2, либо 3, либо 4
                key = temp.substring(4,5); // Буква поля
                key2 = temp.substring(5); // Цифра поля

                System.out.println("Пытаемся поместить кораблик");
                if (size == 1 && oneDeck < 1) throw new Exception("Все доступные кораблики этого вида уже установлены.");
                if (size == 2 && twoDeck < 1) throw new Exception("Все доступные кораблики этого вида уже установлены.");
                if (size == 3 && threeDeck < 1) throw new Exception("Все доступные кораблики этого вида уже установлены.");
                if (size == 4 && fourDeck < 1) throw new Exception("Все доступные кораблики этого вида уже установлены.");

                    postShip = setShip(myField,orientation,size,key,key2);
                    if (postShip) {
                        switch (size){
                            case 1:
                                oneDeck--;
                                break;
                            case 2:
                                twoDeck--;
                                break;
                            case 3:
                                threeDeck--;
                                break;
                            default:
                                fourDeck--;
                                break;
                        }
                    } else {
                        System.out.println("Кораблик не поместился. Попробуйте ещё раз.");
                    }
                System.out.println("Можно разместить ещё корабликов: " +
                        "\nОднопалубных: " + oneDeck +
                        "\nДвухпалубных: " + twoDeck +
                        "\nТрёхпалубных: " + threeDeck +
                        "\nЧетырёхпалубных: " + fourDeck);

                update(myField,myFieldShow);
                printField(myFieldShow, enemyFieldShow);

                //Если мы установили все доступные кораблики, то можно выходить и начинать перестрелку
                if (oneDeck < 1 && twoDeck < 1 && threeDeck < 1 && fourDeck < 1) break;
                }
            catch (Exception exception){
                System.out.println("Вводите по заданной маске, пожалуйста");
            }
        }
    }

    static boolean setShip(char[][] field, int orientation, int size, String keyLetter, String key2Num){
        //Маски для корабликов:
        //единичка - сам корабль, нолик - запретное место

        char[][] tempShip;

        switch (size){
            case 1:
            tempShip = new char[][]{
                    {'0', '0', '0'},
                    {'0', '1', '0'},
                    {'0', '0', '0'}
            };
            break;

            case 2:
                    if(orientation == 1) tempShip = new char[][]{
                            {'0', '0', '0'},
                            {'0', '1', '0'},
                            {'0', '1', '0'},
                            {'0', '0', '0'}
                    };
                    else tempShip = new char[][]{
                            {'0', '0', '0', '0'},
                            {'0', '1', '1', '0'},
                            {'0', '0', '0', '0'}
                    };
                break;

            case 3:
                    if(orientation == 1) tempShip = new char[][]{
                            {'0', '0', '0'},
                            {'0', '1', '0'},
                            {'0', '1', '0'},
                            {'0', '1', '0'},
                            {'0', '0', '0'}
                    };
                    else tempShip = new char[][]{
                            {'0', '0', '0', '0', '0'},
                            {'0', '1', '1', '1', '0'},
                            {'0', '0', '0', '0', '0'}
                    };
                break;

            default:
                    if(orientation == 1) tempShip = new char[][]{
                            {'0', '0', '0'},
                            {'0', '1', '0'},
                            {'0', '1', '0'},
                            {'0', '1', '0'},
                            {'0', '1', '0'},
                            {'0', '0', '0'}
                    };
                    else tempShip = new char[][]{
                            {'0', '0', '0', '0', '0', '0'},
                            {'0', '1', '1', '1', '1', '0'},
                            {'0', '0', '0', '0', '0', '0'}
                    };
                break;
        }

        try{
            //Пытаемся посимвольно переписать наш кораблик в поле
            //Сначала проверяем, что он не конфликтует
            for (int i = 0; i < tempShip.length; i++){
                for (int j = 0; j < tempShip[0].length; j++){
                    if ((field[maskColumn.get(key2Num) + i][maskRow.get(keyLetter) + j] == '1' && tempShip[i][j] == '1') ||
                        (field[maskColumn.get(key2Num) + i][maskRow.get(keyLetter) + j] == '0' && tempShip[i][j] == '1') ||
                        (field[maskColumn.get(key2Num) + i][maskRow.get(keyLetter) + j] == '1' && tempShip[i][j] == '0'))

                        throw new Exception();
                }
            }
            //Если дошли до этого момента, то не конфликтуем. Пытаемся писать:
            for (int i = 0; i < tempShip.length; i++){
                for (int j = 0; j < tempShip[0].length; j++){
                    field[maskColumn.get(key2Num) + i][maskRow.get(keyLetter) + j] = tempShip[i][j];
                }
            }
            return true; // сообщаем, что размещение удачно
        }catch (Exception exception){}
        return false; // сообщаем, что что-то пошло не так
    }

    static void shoot() {
        Scanner scan = new Scanner(System.in);
        boolean shootAgain = true;

        do {
            String temp = scan.nextLine();
            String key, key2;
            try {
                if (temp.length() > 3 || temp.length() < 2) {
                    System.out.println("Вводите только ядрес ячейки, пожалуйста");
                }
                if (temp.length() > 2) {
                    key = temp.substring(0, 1).toUpperCase();
                    key2 = temp.substring(1, 3).toUpperCase(); //второй ключ - десятка, из двух символов
                } else {
                    key = temp.substring(0, 1).toUpperCase();
                    key2 = temp.substring(1, 2).toUpperCase(); //второй ключ - цифра
                }

                if (maskRow.containsKey(key)) {//если наш первый ключ - ключ по буквам
                    System.out.println("Пытаемся попасть в поле " + key + key2);
                    if (enemyField[maskColumn.get(key2) + 1][maskRow.get(key) + 1] == HIT){
                        System.out.println("Сюда уже стреляли! Будь внимательнее в следующий раз. Этот выстрел засчитывается как МИМО!");
                        shootAgain = false;
                    }
                    else if (enemyField[maskColumn.get(key2) + 1][maskRow.get(key) + 1] == '1') {
                        enemyField[maskColumn.get(key2) + 1][maskRow.get(key) + 1] = HIT;
                        compShips--;
                        if (compShips <= 0) break;
                        System.out.println("Успешное попадание! Стреляй ещё раз!");
                        surroundShipWithDots(enemyField,maskColumn.get(key2) + 1,maskRow.get(key) + 1);
                        updateHideShips(enemyField,enemyFieldShow);
                        printField(myFieldShow,enemyFieldShow);
                    }
                    else{
                        enemyField[maskColumn.get(key2) + 1][maskRow.get(key) + 1] = MISS;
                        System.out.println("МИМО!");
                        shootAgain = false;
                    }
                } else { //значит наш второй ключ - ключ по буквам
                    System.out.println("Неверные координаты.");
                    }
            } catch (Exception ex) {
                System.out.println("Вводите верные координаты, пожалуйста");
            }
        } while (shootAgain);
    }

        static void compPutShip(){
            //компьютер пытается разместить корабли используя метод для игрока, но со случайными входными данными
            //форма:
            //static boolean setShip(char[][] field, int orientation, int size, String keyLetter, String key2Num)

            Random random = new Random();
            String[] Letters = {"А","Б","В","Г","Д","Е","Ж","З","И","К"};
            String[] Numbers = {"1","2","3","4","5","6","7","8","9","10"};
            int allOK = 0;
            while(allOK !=10) {
                allOK = 0;
                clearField(enemyField);
                for (int i = 0; i < 10; i++) {
                    if (setShip(enemyField, random.nextInt(2) + 1, 4, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                        allOK++;
                        break;
                    }
                }
                if (allOK > 0){
                for (int i = 0; i < 10; i++) {
                    if (setShip(enemyField, random.nextInt(2) + 1, 3, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                        allOK++;
                        break;
                    }
                }
                }
                if (allOK > 1){
                for (int i = 0; i < 10; i++) {
                    if (setShip(enemyField, random.nextInt(2) + 1, 3, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                        allOK++;
                        break;
                    }
                }}

                if (allOK > 2) {
                    for (int i = 0; i < 20; i++) {
                        if (setShip(enemyField, random.nextInt(2) + 1, 2, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                            allOK++;
                            break;
                        }
                    }
                }
                if(allOK > 3) {
                    for (int i = 0; i < 20; i++) {
                        if (setShip(enemyField, random.nextInt(2) + 1, 2, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                            allOK++;
                            break;
                        }
                    }
                }
                if (allOK > 4) {
                    for (int i = 0; i < 20; i++) {
                        if (setShip(enemyField, random.nextInt(2) + 1, 2, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                            allOK++;
                            break;
                        }
                    }
                }

                if(allOK > 5) {
                    for (int i = 0; i < 30; i++) {
                        if (setShip(enemyField, 1, 1, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                            allOK++;
                            break;
                        }
                    }
                }
                if (allOK > 6) {
                    for (int i = 0; i < 30; i++) {
                        if (setShip(enemyField, 1, 1, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                            allOK++;
                            break;
                        }
                    }
                }
                if (allOK > 7) {
                    for (int i = 0; i < 30; i++) {
                        if (setShip(enemyField, 1, 1, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                            allOK++;
                            break;
                        }
                    }
                }
                if (allOK > 8) {
                    for (int i = 0; i < 30; i++) {
                        if (setShip(enemyField, 1, 1, Letters[random.nextInt(10)], Numbers[random.nextInt(10)])) {
                            allOK++;
                            break;
                        }
                    }
                }
                System.out.println("Пытаемся сгенерировать поле");
            }
        }

        static void compShoot(){
            Map<Integer, String> mask = new HashMap<>();
            mask.put( 0,"А");
            mask.put( 1,"Б");
            mask.put( 2,"В");
            mask.put( 3,"Г");
            mask.put( 4,"Д");
            mask.put( 5,"Е");
            mask.put( 6,"Ж");
            mask.put( 7,"З");
            mask.put( 8,"И");
            mask.put( 9,"К");
            Random random = new Random();
            boolean empty = true;
            int row,column;
            do{
                row = random.nextInt(10);
                column = random.nextInt(10);

                if(myField[row+1][column+1] == '1') {
                    myField[row+1][column+1] = HIT;
                    System.out.println("Компьютер стреляет в " + mask.get(column) + (row +1) + " и попадает!");

                    surroundShipWithDots(myField,row+1,column+1);

                } else if (myField[row+1][column+1] == MISS || myField[row+1][column+1] == HIT){

                }
                else{
                    myField[row+1][column+1] = MISS;
                    System.out.println("Компьютер стреляет в " + mask.get(column) + (row +1) + ". МИМО!");
                    empty = false;
                }
            }while(empty);
        }

        static boolean whoWin(int shipsNum){
            if (shipsNum <= 0) return true;
            else return false;
        }

        //возвращаем true если только обнаружили в указанных координатах кораблик и окружили его точками
        static boolean surroundShipWithDots(char[][] field, int keyLetter, int key2Num){
            //Нам надо проверить, что сверху, снизу, слева и справа нет продолжения корабля
            if(field[keyLetter][key2Num] == HIT){
                //смотрим вокруг корабля
                lookAroundAndFillTheField(field,keyLetter,key2Num);
            }
            return false;
        }

        static boolean lookAroundAndFillTheField (char[][] field, int x, int y){
        //x и y = координаты указанного поля

            //идём к самому правому нижнему краю
            while (field[x][y+1] == HIT){
                y+=1;
            }
            while (field[x+1][y] == HIT){
                x+=1;
            }
            if (field[x-1][y] == '1') return false;
            if (field[x][y-1] == '1') return false;
            if (field[x][y+1] == '1') return false;
            if (field[x+1][y] == '1') return false;

            //идём к самому верхнему левому краю
            while (field[x][y-1] == HIT){
                y-=1;
            }
            while (field[x-1][y] == HIT){
                x-=1;
            }

            if (field[x-1][y] == '1') return false;
            if (field[x][y-1] == '1') return false;
            if (field[x][y+1] == '1') return false;
            if (field[x+1][y] == '1') return false;

            //Мы точно знаем, что по бокам от нашей точки нет целых кусков кораблей. Можно начинать проходиться по кораблю и закрашивать его
            fillFieldWithDots (field, x, y);
            while (field[x+1][y] == HIT){
                x++;
                fillFieldWithDots (field, x, y);
            }
            while (field[x][y+1] == HIT){
                y++;
                fillFieldWithDots (field, x, y);
            }
            return true;
        }

        static void fillFieldWithDots (char[][] field, int x, int y){
            if (field[x-1][y-1]!= HIT)     field[x-1][y-1]     = MISS;
            if (field[x-1][y]  != HIT)     field[x-1][y]       = MISS;
            if (field[x-1][y+1]!= HIT)     field[x-1][y+1]     = MISS;
            if (field[x][y-1]  != HIT)     field[x][y-1]       = MISS;
            if (field[x][y+1]  != HIT)     field[x][y+1]       = MISS;
            if (field[x+1][y-1]!= HIT)     field[x+1][y-1]     = MISS;
            if (field[x+1][y]  != HIT)     field[x+1][y]       = MISS;
            if (field[x+1][y+1]!= HIT)     field[x+1][y+1]     = MISS;
        }

        static void clearField(char[][] field){
            for(char[] tmp : field){
                for(char temp : tmp){
                    temp = '_';
                }
            }
        }
}