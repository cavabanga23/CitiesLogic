import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 1. запит на слово від юзера
 * +2. знайти і відправити слово рендом
 * +3. записати слово у список використаних
 * 4. отримати слово від юзера
 * + 5. перевірити, чи це дійсно місто, якщо ні - до п.4
 * + 6. перевірити, чи місто не в списку використаних, якщо T - тповернутись до п.4
 * + 7. перевірити відповідність букв (остання від компа - перша від юзера), якщо ні - до п.4
 * + 8. якщо букви відповідають - записати слово до використаних
 * + 9. пошук міста на останню букву міста юзера
 * + 10. перевірка серед використаних
 * + 11. повернути юзеру слово на його останню букву
 * + 12. запис слова до використаних
 */


public class Main {
    char lastLetter;
    HashSet<String> usedWords = new HashSet<>();
    Set<String> cities = new HashSet<>();
    BufferedReader in = null;

    {
        try {
            in = new BufferedReader(new FileReader("src/resources/list.txt"));
            for (String city; (city = in.readLine()) != null; ) {
                cities.add(city);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        String city = main.getRandomCity();
        System.out.println(city);
        main.usersInput(city);
    }

    public void usersInput(String city) {
        lastLetter = getLastLetter(city);
        while (true) {
            Scanner sc = new Scanner(System.in);
            String newCity = sc.nextLine();
            wordTrueChecking(newCity);
            getAnswerCity(newCity);
        }
    }

    public void usersSecondInput() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String newCity = sc.nextLine();
            wordTrueChecking(newCity);
            getAnswerCity(newCity);
        }
    }

    public String getAnswerCity(String word) {
        String compWord = new String();
        String firstLetter = String.valueOf(getLastLetter(word));
        ArrayList<String> citiesList = new ArrayList<>(cities);
        for (String s : citiesList) {
            if (s.startsWith(firstLetter.toUpperCase())) {
                compWord = s;
                usedWords.add(compWord);
                cities.remove(compWord);
                break;
            }
        }
        lastLetter = getLastLetter(compWord);
        System.out.println("Вы ввели " + word + ", мой ход " + compWord + ", вам на букву " + lastLetter);

        return compWord;
    }

    public void wordTrueChecking(String word) {
        if (!cities.contains(word)) {
            System.out.println("Вы написали город, которого не существует, или город уже назван введите реальный город");
            usersSecondInput();
        } else {
            wordConsistChecking(word);
            cities.remove(word);
        }

    }

    public void wordConsistChecking(String word) {
        if (word.startsWith(String.valueOf(lastLetter))) {
            if (!usedWords.contains(word)) {
                usedWords.add(word);
            } else {
                System.out.println("Это слово уже использовано, попробуйте другое");
                usersSecondInput();
            }
        } else {
            System.out.println("Вы ввели город не на ту букву, введите другой ");
            usersSecondInput();
        }

        System.out.println(usedWords + " : эти слова уже назвали");
    }

    public String getRandomCity() throws IOException {
        ArrayList<String> arrayList = new ArrayList<>(cities);
        String city = arrayList.get((int) (Math.random() * 1000));
        if (city.charAt(city.length() - 1) == 'ь' || city.charAt(city.length() - 1) == 'ы' || city.charAt(city.length() - 1) == 'ъ') {
            System.out.println("Теперь введите слово, которое начинается на букву " + city.charAt(city.length() - 2));
        } else if (city.charAt(city.length() - 1) == 'й') {
            System.out.println("Теперь введите слово, которое начинается на букву И");
        } else
            System.out.println("Теперь введите слово, которое начинается на букву " + city.charAt(city.length() - 1));
        return city;
    }

    public char getLastLetter(String city) {
        int last = city.length() - 1;
        char lastChar = city.toUpperCase().charAt(last);
        if (city.toUpperCase().charAt(last) == 'Й') {
            lastChar = 'И';
        } else if (lastChar == 'Ь' || lastChar == 'Ы' || lastChar == 'Ъ') {
            lastChar = city.toUpperCase().charAt(last - 1);
        }
        return lastChar;
    }
}
