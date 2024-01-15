package org.example;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {
    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        String term = getUserInput();
        String url = buildUrl(term);
        String page = downloadWebPage(url);
        printResult(page);
        pageWriter(page);
    }

    private static void pageWriter(String page) throws IOException {
        File output = new File("C:\\Users\\Marat\\Desktop\\PassGitHab.txt");
        FileWriter writer = new FileWriter(output);

        writer.write(page);
        writer.flush();
        writer.close();
    }


    private static void printResult(String page) {
        int start = page.indexOf("wrapperType") + 14;
        int end = page.indexOf("\",", start);
        StringBuilder result = new StringBuilder();
        System.out.println(result);

        String wrapperType = page.substring(start, end);

        if (wrapperType.equals("audiobook")) {
            result = buildBookResult(page);

        } else if (wrapperType.equals("track")) {
            String kind = getKind(page);
            if (kind.equals("song")) {
                result = buildSongInformation(page);
            } else if (kind.equals("feature_movie")) {
                result = buildMovieInformation(page);
            }
        }
        if(result.length() == 0){
            result.append("Unknown result: ");
            result.append(page);
        }
        System.out.println(result.toString());
    }
    static StringBuilder buildMovieInformation(String page) {
        int end, start;
        StringBuilder result = new StringBuilder();
        start = page.indexOf("trackName") + "trackName".length() + 3;
        end = page.indexOf("\",", start);
        String movieName = page.substring(start, end);

        start = page.indexOf("longDescription") + "longDescription".length() + 3;
        end = page.indexOf("\",", start);
        String movieDescription = page.substring(start, end).replaceAll("<br />", "\n");

        result.append("This is a movie.\n");
        result.append(movieName);
        result.append(" \n Description: \n ");
        result.append(movieDescription);
        return result;
    }

    static StringBuilder buildSongInformation(String page) {
        int end, start;
        StringBuilder result = new StringBuilder();

        start = page.indexOf("artistName") + "artistName".length() + 3;
        end = page.indexOf("\",", start);
        String artistName = page.substring(start, end);

        start = page.indexOf("primaryGenreName") + "primaryGenreName".length() + 3;
        end = page.indexOf("\",", start);
        String primaryGenreName = page.substring(start, end);

        start = page.indexOf("trackCensoredName") + "trackCensoredName".length() + 3;
        end = page.indexOf("\",", start);
        String trackCensoredName = page.substring(start, end);

        start = page.indexOf("country") + "country".length() + 3;
        end = page.indexOf("\",", start);
        String country = page.substring(start, end);

        result.append("This is a song.\n");
        result.append(artistName);
        result.append("\nОтслеживаемое имя: " + trackCensoredName);
        result.append("\nПервое имя: " + primaryGenreName);
        result.append("\nCountry: " + country);
        return result;
    }
        private static String getKind(String page) {
        int start;
        int end;
        start = page.indexOf("kind") + "kind".length() + 3;
        end = page.indexOf("\",", start);
        String kind = page.substring(start, end);
        return kind;
    }

    private static StringBuilder buildBookResult(String page) {
        StringBuilder builder = new StringBuilder();
        int start;
        int end;
        start = page.indexOf("artistName") +13;
        end = page.indexOf("\",", start);
        String author = page.substring(start, end);

        start = page.indexOf("collectionName") + "collectionName".length() + 3;
        end = page.indexOf("\",", start);
        String bookName = page.substring(start, end);

        start = page.indexOf("description") + "description".length() + 3;
        end = page.indexOf("\"", start);
        String bookDescription = page.substring(start, end);

        builder.append("This is a book. Author is ");
        builder.append(author);
        builder.append(".\nThe name is: ");
        builder.append(bookName);
        builder.append("\nDescription: \n");
        builder.append(bookDescription);
        return builder;
    }



    private static String buildUrl(String term) {
        String termWithoutSpaces = term.replaceAll(" ", "+");
        String itunesApi = "https://itunes.apple.com/search?term=";
        String limitParam = "&limit=1";
        StringBuilder url = new StringBuilder();
        url.append(itunesApi);
        url.append(termWithoutSpaces);
        url.append(limitParam);
       // String url = itunesApi + termWithoutSpaces + limitParam;
        return url.toString();
    }

    static String getUserInput( ){
        System.out.println("What you looking for in itunes");
        Scanner scanner = new Scanner(System.in);
        String info = scanner.nextLine();
        return info;
    }

    private static String downloadWebPage(String url) throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        URLConnection urlConnection = new URL(url).openConnection();
        try (InputStream is = urlConnection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                result.append(line);}
        }
        return result.toString();

   /*     Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter 5 words: ");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 5; i++){
            String word = scanner.next();
            builder.insert(0, word);
            builder.insert(0, " ");
        }
        System.out.println(builder.toString());

*/

       /* StringBuilder builder = new StringBuilder();

        builder.append("Зима недаром злится,\n");
        builder.append("Прошла ее пора —\n");
        builder.append("Весна в окно стучится\n");
        builder.append("И гонит со двора.");
        String str = String.valueOf(builder);
        System.out.println(str);

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < builder.length(); i++){
            char ch = builder.charAt(i);
            builder.deleteCharAt(i);
            if(i % 2 == 0){
                char upperCaseChar = Character.toUpperCase(ch);
                builder.insert(i, upperCaseChar);
            }else {
                char lowerCaseChar = Character.toLowerCase(ch);
                builder.insert(i, lowerCaseChar);
            }
        }
        String output = builder.toString();
        System.out.println(output);
*/
    }
}