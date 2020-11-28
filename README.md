# Hospital Database
This program imports, queries, and prints reports from hospital text files.

Features:
- Full query menu
- Creates a new database file labelled 'database.sl3' if file does not exist
- Automatically checks whether a Person text file or Treatment text file was entered
- Foreign key restrictions to prevent invalid entries
- Exception handling
- Report printing to spreadsheet

Parameters:
- Text file input must be labelled Person.txt or Treatment.txt
- Some queries may not work if both Person and Treatment have not been inserted
- Classpath to .jar file is required to run