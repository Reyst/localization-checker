# localization-checker
Small application for checking localization in android apps

This application generates report in excel-format about you translations. It watchs you resources, detects used languages and creates xls-table with ids of the strings and their translations. If a translation is absent, the key of the is been highlighted with the red color.

How to use:
java -jar localization-checker.jar <resource_folder> [filename_of_report.xls]
