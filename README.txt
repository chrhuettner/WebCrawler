SetUp:
- Pull GitHub repository
- Execute the gradle task build
- In the directory build/libs you should now see the generated .jar-File
- Execute the .jar-File in the console (java -jar .\WebCrawler-1.0-SNAPSHOT.jar)
- Explanation is given in the console (You can enter a Language like 'English', 'Swedish' etc.)
- The Website-Links must be given with 'https://'
- If you want to input more than 1 website use a 'space' in between them. The depth must also be defined by the user for every website, the same goes for the target language.
- You can find the created file in the build/libs directory
