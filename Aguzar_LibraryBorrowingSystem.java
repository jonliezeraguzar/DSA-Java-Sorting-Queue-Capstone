import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


    static Scanner sc = new Scanner(System.in);

    //  Book records (parallel ArrayLists) 
    static ArrayList<Integer> bookIds = new ArrayList<>();
    static ArrayList<String> bookTitles = new ArrayList<>();
    static ArrayList<String> bookAuthors = new ArrayList<>();
    static ArrayList<Integer> bookYears = new ArrayList<>();
    static ArrayList<Integer> bookCopies = new ArrayList<>();

    //  Borrowing request records (parallel ArrayLists) 
    static ArrayList<Integer> requestNumbers = new ArrayList<>();
    static ArrayList<String> requestStudentIds = new ArrayList<>();
    static ArrayList<String> requestStudentNames = new ArrayList<>();
    static ArrayList<Integer> requestBookIds = new ArrayList<>();
    static ArrayList<String> requestStatuses = new ArrayList<>();

    //  FIFO queue of pending request numbers 
    static Queue<Integer> pendingQueue = new LinkedList<>();

    //  Counters 
    static int nextBookId = 1;
    static int nextRequestNo = 1001;

        void main() {
        loadSampleBooks();
        int choice;

        do {
            showMenu();
            choice = readInt("Enter choice: ", 0, 14);
            System.out.println();

            // Run the operation that matches the menu choice
            switch (choice) {
                case 1:  addBook(); break;
                case 2:  displayBooks(); break;
                case 3:  searchBook(); break;
                case 4:  updateBookCopies(); break;
                case 5:  removeBook(); break;
                case 6:  sortBooksMenu(); break;
                case 7:  registerRequest(); break;
                case 8:  processNextRequest(); break;
                case 9:  peekNextRequest(); break;
                case 10: displayPendingQueue(); break;
                case 11: checkQueueEmpty(); break;
                case 12: displayAllRequests(); break;
                case 13: sortRequestsMenu(); break;
                case 14: cancelRequest(); break;
                case 0:  System.out.println("Thank you for using the Library Borrowing Request System. Goodbye!"); break;
            }
        } while (choice != 0);

        sc.close();
    }

    // =====================================================================
    // MENU
    // =====================================================================
    // Print the list of menu options.
    static void showMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("   LIBRARY BORROWING REQUEST SYSTEM");
        System.out.println("==========================================");
        System.out.println(" BOOK RECORDS");
        System.out.println("  1  - Add a book");
        System.out.println("  2  - Display all books");
        System.out.println("  3  - Search a book");
        System.out.println("  4  - Update number of copies");
        System.out.println("  5  - Remove a book");
        System.out.println("  6  - Sort books (title / publication year)");
        System.out.println(" BORROWING REQUESTS (QUEUE)");
        System.out.println("  7  - Register borrowing request (enqueue)");
        System.out.println("  8  - Process next request (dequeue)");
        System.out.println("  9  - Peek at next request");
        System.out.println("  10 - Display pending requests");
        System.out.println("  11 - Check if queue is empty");
        System.out.println("  12 - Display all borrowing records");
        System.out.println("  13 - Sort borrowing records (student name / request no.)");
        System.out.println("  14 - Cancel a pending request");
        System.out.println("  0  - Exit");
        System.out.println("------------------------------------------");
    }

    // =====================================================================
    // INPUT HELPERS (validation - the program never crashes on bad input)
    // =====================================================================
    // Ask for a whole number between min and max; keep asking until the input is valid.
    static int readInt(String prompt, int min, int max) {
        // Repeat until the user types a valid number
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                // parseInt throws NumberFormatException when the text is not a whole number
                int value = Integer.parseInt(line);
                // Accept the number only if it is inside the allowed range
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("  Invalid input. Enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    // Ask for text; keep asking until the user types something that is not empty.
    static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            // Accept the text only if something was actually typed
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("  Input cannot be empty. Please try again.");
        }
    }

    // =====================================================================
    // SAMPLE DATA
    // =====================================================================
    // Fill the book lists with starting data so the program can be demonstrated right away.
    static void loadSampleBooks() {
        insertBook("The Five People You Meet in Heaven", "Mitch Albom", 2003, 3);
        insertBook("Demian", "Herman Hesse", 1919, 1);
        insertBook("They Both Die at the End", "Adam Silvera", 2017, 2);
        insertBook("Have a Little Faith", "Mitch Albom", 2009, 1);
        insertBook("To Kill a Mockingbird", "Harper Lee", 1960, 2);
        insertBook("Atomic Habits", "James Clear", 2018, 3);
        insertBook("Noli Me Tangere", "Jose Rizal", 1887, 2);
    }

    // Append one book to every book list (same position = same book) and move to the next book ID.
    static void insertBook(String title, String author, int year, int copies) {
        bookIds.add(nextBookId);
        bookTitles.add(title);
        bookAuthors.add(author);
        bookYears.add(year);
        bookCopies.add(copies);
        nextBookId++;
    }

    // =====================================================================
    // BOOK OPERATIONS
    // =====================================================================
    // Ask the user for the details of a new book and add it to the catalog.
    static void addBook() {
        System.out.println("--- ADD BOOK ---");
        String title = readText("Title: ");
        String author = readText("Author: ");
        int year = readInt("Publication year (1000-2100): ", 1000, 2100);
        int copies = readInt("Number of copies (1-100): ", 1, 100);

        insertBook(title, author, year, copies);
        System.out.println("Book added successfully with ID " + (nextBookId - 1) + ".");
    }

    // Print every book in a table.
    static void displayBooks() {
        System.out.println("--- BOOK RECORDS ---");
        if (bookIds.isEmpty()) {
            System.out.println("No books in the library yet.");
            return;
        }
        System.out.printf("%-5s %-42s %-24s %-6s %-6s%n", "ID", "Title", "Author", "Year", "Copies");
        System.out.println("---------------------------------------------------------------------------------------");
        for (int i = 0; i < bookIds.size(); i++) {
            System.out.printf("%-5d %-42s %-24s %-6d %-6d%n",
                    bookIds.get(i), bookTitles.get(i), bookAuthors.get(i),
                    bookYears.get(i), bookCopies.get(i));
        }
    }

    // Linear search: show every book whose title or author contains the keyword.
    static void searchBook() {
        System.out.println("--- SEARCH BOOK ---");
        // Lower-case the keyword and the book text so the search ignores capitalization
        String keyword = readText("Enter title or author keyword: ").toLowerCase();
        // found lets us print the table header once and detect "no match" at the end
        boolean found = false;

        for (int i = 0; i < bookIds.size(); i++) {
            if (bookTitles.get(i).toLowerCase().contains(keyword)
                    || bookAuthors.get(i).toLowerCase().contains(keyword)) {
                if (!found) {
                    System.out.printf("%-5s %-42s %-24s %-6s %-6s%n", "ID", "Title", "Author", "Year", "Copies");
                    found = true;
                }
                System.out.printf("%-5d %-42s %-24s %-6d %-6d%n",
                        bookIds.get(i), bookTitles.get(i), bookAuthors.get(i),
                        bookYears.get(i), bookCopies.get(i));
            }
        }
        if (!found) {
            System.out.println("No matching book found.");
        }
    }

    // Change the number of copies of a book chosen by its ID.
    static void updateBookCopies() {
        System.out.println("--- UPDATE COPIES ---");
        if (bookIds.isEmpty()) {
            System.out.println("No books to update.");
            return;
        }
        displayBooks();
        int id = readInt("Enter Book ID to update: ", 1, Integer.MAX_VALUE);
        int idx = bookIds.indexOf(id);
        if (idx == -1) {
            System.out.println("Book ID not found.");
            return;
        }
        int copies = readInt("New number of copies (0-100): ", 0, 100);
        bookCopies.set(idx, copies);
        System.out.println("Copies updated for \"" + bookTitles.get(idx) + "\".");
    }

    // Delete a book from all book lists, unless a pending request still needs it.
    static void removeBook() {
        System.out.println("--- REMOVE BOOK ---");
        if (bookIds.isEmpty()) {
            System.out.println("No books to remove.");
            return;
        }
        displayBooks();
        int id = readInt("Enter Book ID to remove: ", 1, Integer.MAX_VALUE);
        int idx = bookIds.indexOf(id);
        if (idx == -1) {
            System.out.println("Book ID not found.");
            return;
        }

        // A book cannot be removed while a pending request still needs it.
        for (int i = 0; i < requestNumbers.size(); i++) {
            if (requestBookIds.get(i) == id && requestStatuses.get(i).equals("Pending")) {
                System.out.println("Cannot remove: there are pending requests for this book.");
                return;
            }
        }

        // Remove the same position from every book list so the records stay aligned
        String removedTitle = bookTitles.get(idx);
        bookIds.remove(idx);       // idx is an int -> removes by position
        bookTitles.remove(idx);
        bookAuthors.remove(idx);
        bookYears.remove(idx);
        bookCopies.remove(idx);
        System.out.println("\"" + removedTitle + "\" has been removed.");
    }

    // =====================================================================
    // SWAP HELPERS (keep all parallel lists aligned while sorting)
    // =====================================================================
    // Swap two positions in a list of Strings.
    static void swapStringList(ArrayList<String> list, int i, int j) {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Swap two positions in a list of Integers.
    static void swapIntList(ArrayList<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Swap two whole book records so all book lists stay aligned.
    static void swapBooks(int i, int j) {
        swapIntList(bookIds, i, j);
        swapStringList(bookTitles, i, j);
        swapStringList(bookAuthors, i, j);
        swapIntList(bookYears, i, j);
        swapIntList(bookCopies, i, j);
    }

    // Swap two whole request records so all request lists stay aligned.
    static void swapRequests(int i, int j) {
        swapIntList(requestNumbers, i, j);
        swapStringList(requestStudentIds, i, j);
        swapStringList(requestStudentNames, i, j);
        swapIntList(requestBookIds, i, j);
        swapStringList(requestStatuses, i, j);
    }

    // =====================================================================
    // SORTING - BOOKS
    // =====================================================================
    // Let the user choose how to sort the books, and show the list before and after.
    static void sortBooksMenu() {
        System.out.println("--- SORT BOOKS ---");
        if (bookIds.size() < 2) {
            System.out.println("Need at least 2 books to sort.");
            return;
        }
        System.out.println("1 - Sort by Title (Bubble Sort)");
        System.out.println("2 - Sort by Publication Year (Insertion Sort)");
        System.out.println("0 - Back");
        int choice = readInt("Choice: ", 0, 2);
        if (choice == 0) {
            return;
        }

        System.out.println("\nBEFORE SORTING:");
        displayBooks();

        if (choice == 1) {
            bubbleSortBooksByTitle();
            System.out.println("\nAFTER SORTING (Title A-Z, Bubble Sort):");
        } else {
            insertionSortBooksByYear();
            System.out.println("\nAFTER SORTING (Year oldest-newest, Insertion Sort):");
        }
        displayBooks();
    }

    // Bubble Sort: repeatedly swaps adjacent out-of-order titles.
    // Suitable here because the catalog is small and the "swapped" flag lets it stop early.
    static void bubbleSortBooksByTitle() {
        int n = bookTitles.size();
        // Each pass pushes the alphabetically last title of the unsorted part to its final place
        for (int i = 0; i < n - 1; i++) {
            // swapped stays false if a whole pass needs no swap
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                // Compare neighbours ignoring capitalization; swap them if they are out of order
                if (bookTitles.get(j).compareToIgnoreCase(bookTitles.get(j + 1)) > 0) {
                    swapBooks(j, j + 1);
                    swapped = true;
                }
            }
            // No swaps in this pass means the list is already sorted, so stop early
            if (!swapped) {
                break;
            }
        }
    }

    // Insertion Sort: builds a sorted section one record at a time.
    // Suitable for years because new books are added at the end of an already
    // mostly-sorted list, which is the case where insertion sort is fastest.
    static void insertionSortBooksByYear() {
        int n = bookYears.size();
        // Everything before position i is already sorted; insert record i into its correct place
        for (int i = 1; i < n; i++) {
            int j = i;
            // Move the record left while the one before it has a later year
            while (j > 0 && bookYears.get(j - 1) > bookYears.get(j)) {
                swapBooks(j, j - 1);
                j--;
            }
        }
    }

    // =====================================================================
    // QUEUE / REQUEST OPERATIONS
    // =====================================================================
    // Save a new borrowing request in the request lists and enqueue its number (FIFO).
    static void registerRequest() {
        System.out.println("--- REGISTER BORROWING REQUEST ---");
        if (bookIds.isEmpty()) {
            System.out.println("There are no books in the catalog to request.");
            return;
        }
        String studentId = readText("Student ID: ");
        String studentName = readText("Student name: ");

        displayBooks();
        int bookId = readInt("Enter Book ID to borrow: ", 1, Integer.MAX_VALUE);
        // indexOf returns -1 when no book has this ID
        int bookIdx = bookIds.indexOf(bookId);
        if (bookIdx == -1) {
            System.out.println("Book ID not found. Request not registered.");
            return;
        }

        // 1) store the record in the ArrayLists
        requestNumbers.add(nextRequestNo);
        requestStudentIds.add(studentId);
        requestStudentNames.add(studentName);
        requestBookIds.add(bookId);
        requestStatuses.add("Pending");

        // 2) enqueue the request number (FIFO)
        pendingQueue.add(nextRequestNo);

        System.out.println("Request #" + nextRequestNo + " registered and added to the queue.");
        System.out.println("Position in queue: " + pendingQueue.size());
        nextRequestNo++;
    }

    // Dequeue the request at the front of the line and approve or reject it based on available copies.
    static void processNextRequest() {
        System.out.println("--- PROCESS NEXT REQUEST ---");
        if (pendingQueue.isEmpty()) {
            System.out.println("The queue is empty. No pending requests to process.");
            return;
        }

        int requestNo = pendingQueue.poll();          // dequeue (front of the line)
        int requestIndex = requestNumbers.indexOf(requestNo);   // find its record
        int bookIdx = bookIds.indexOf(requestBookIds.get(requestIndex));

        System.out.println("Processing request #" + requestNo + " from "
                + requestStudentNames.get(requestIndex) + " (" + requestStudentIds.get(requestIndex) + ")");

        // Case 1: the book is no longer in the catalog
        if (bookIdx == -1) {
            requestStatuses.set(requestIndex, "Rejected (book unavailable)");
            System.out.println("Result: REJECTED - the book is no longer in the catalog.");
        } else if (bookCopies.get(bookIdx) > 0) {
            // Case 2: a copy is available, so lend it and reduce the stock by one
            bookCopies.set(bookIdx, bookCopies.get(bookIdx) - 1);
            requestStatuses.set(requestIndex, "Approved");
            System.out.println("Result: APPROVED - \"" + bookTitles.get(bookIdx) + "\" is ready for pickup.");
            System.out.println("Copies left: " + bookCopies.get(bookIdx));
        } else {
            // Case 3: the book exists but every copy is already borrowed
            requestStatuses.set(requestIndex, "Rejected (no copies)");
            System.out.println("Result: REJECTED - no copies of \"" + bookTitles.get(bookIdx) + "\" are available.");
        }
        System.out.println("Requests still waiting: " + pendingQueue.size());
    }

    // Show the request at the front of the queue without removing it.
    static void peekNextRequest() {
        System.out.println("--- NEXT REQUEST IN LINE ---");
        if (pendingQueue.isEmpty()) {
            System.out.println("The queue is empty. Nothing to peek at.");
            return;
        }
        int requestNo = pendingQueue.peek();          // look without removing
        int requestIndex = requestNumbers.indexOf(requestNo);
        int bookIdx = bookIds.indexOf(requestBookIds.get(requestIndex));
        System.out.println("Request #" + requestNo);
        System.out.println("Student: " + requestStudentNames.get(requestIndex) + " (" + requestStudentIds.get(requestIndex) + ")");
        System.out.println("Book   : " + (bookIdx == -1 ? "(removed)" : bookTitles.get(bookIdx)));
    }

    // Show all waiting requests from front to back without removing them.
    static void displayPendingQueue() {
        System.out.println("--- PENDING REQUESTS (front -> back) ---");
        if (pendingQueue.isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }
        System.out.printf("%-4s %-9s %-12s %-22s %-40s%n", "Pos", "Request#", "Student ID", "Student Name", "Book");
        System.out.println("------------------------------------------------------------------------------------------");
        int position = 1;
        for (int requestNo : pendingQueue) {          // iterating does not remove items
            int requestIndex = requestNumbers.indexOf(requestNo);
            int bookIdx = bookIds.indexOf(requestBookIds.get(requestIndex));
            System.out.printf("%-4d %-9d %-12s %-22s %-40s%n",
                    position, requestNo, requestStudentIds.get(requestIndex), requestStudentNames.get(requestIndex),
                    (bookIdx == -1 ? "(removed)" : bookTitles.get(bookIdx)));
            position++;
        }
        System.out.println("Total pending: " + pendingQueue.size());
    }

    // Tell the user whether any requests are waiting.
    static void checkQueueEmpty() {
        if (pendingQueue.isEmpty()) {
            System.out.println("The queue is EMPTY.");
        } else {
            System.out.println("The queue is NOT empty. Pending requests: " + pendingQueue.size());
        }
    }

    // Remove one specific pending request from the queue and mark it as Cancelled.
    static void cancelRequest() {
        System.out.println("--- CANCEL PENDING REQUEST ---");
        if (pendingQueue.isEmpty()) {
            System.out.println("The queue is empty. Nothing to cancel.");
            return;
        }
        displayPendingQueue();
        int requestNo = readInt("Enter Request # to cancel: ", 1, Integer.MAX_VALUE);
        int requestIndex = requestNumbers.indexOf(requestNo);
        // Only a request that is still waiting in the queue can be cancelled
        if (requestIndex == -1 || !requestStatuses.get(requestIndex).equals("Pending")) {
            System.out.println("That request is not pending, so it cannot be cancelled.");
            return;
        }
        pendingQueue.remove(Integer.valueOf(requestNo));   // remove this specific item from the queue
        requestStatuses.set(requestIndex, "Cancelled");
        System.out.println("Request #" + requestNo + " has been cancelled.");
    }

    // =====================================================================
    // BORROWING RECORDS - DISPLAY & SORT
    // =====================================================================
    // Print every borrowing record, including approved, rejected and cancelled ones.
    static void displayAllRequests() {
        System.out.println("--- ALL BORROWING RECORDS ---");
        if (requestNumbers.isEmpty()) {
            System.out.println("No borrowing records yet.");
            return;
        }
        System.out.printf("%-9s %-12s %-22s %-40s %-28s%n", "Request#", "Student ID", "Student Name", "Book", "Status");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < requestNumbers.size(); i++) {
            int bookIdx = bookIds.indexOf(requestBookIds.get(i));
            System.out.printf("%-9d %-12s %-22s %-40s %-28s%n",
                    requestNumbers.get(i), requestStudentIds.get(i), requestStudentNames.get(i),
                    (bookIdx == -1 ? "(removed)" : bookTitles.get(bookIdx)), requestStatuses.get(i));
        }
    }

    // Let the user choose how to sort the borrowing records, and show them before and after.
    static void sortRequestsMenu() {
        System.out.println("--- SORT BORROWING RECORDS ---");
        if (requestNumbers.size() < 2) {
            System.out.println("Need at least 2 borrowing records to sort.");
            return;
        }
        System.out.println("1 - Sort by Student Name (Selection Sort)");
        System.out.println("2 - Sort by Request Number (Insertion Sort)");
        System.out.println("0 - Back");
        int choice = readInt("Choice: ", 0, 2);
        if (choice == 0) {
            return;
        }

        System.out.println("\nBEFORE SORTING:");
        displayAllRequests();

        if (choice == 1) {
            selectionSortRequestsByName();
            System.out.println("\nAFTER SORTING (Student Name A-Z, Selection Sort):");
        } else {
            insertionSortRequestsByNumber();
            System.out.println("\nAFTER SORTING (Request No. ascending, Insertion Sort):");
        }
        displayAllRequests();
        System.out.println("(The pending queue order is unchanged: it stores request numbers, not positions.)");
    }

    // Selection Sort: finds the alphabetically smallest name in the unsorted part
    // and places it at the front. Suitable because it makes very few swaps, and
    // each swap moves a whole record (5 lists).
    static void selectionSortRequestsByName() {
        int n = requestStudentNames.size();
        // Position i will receive the alphabetically smallest name of the unsorted part
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            // Scan the rest of the list to find the smallest name
            for (int j = i + 1; j < n; j++) {
                if (requestStudentNames.get(j).compareToIgnoreCase(requestStudentNames.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            // Swap only when a smaller name was found, which avoids useless swaps
            if (minIdx != i) {
                swapRequests(i, minIdx);
            }
        }
    }

    // Insertion Sort: restores request-number order. Records normally arrive in
    // ascending order, so the list is nearly sorted and insertion sort is fast.
    static void insertionSortRequestsByNumber() {
        int n = requestNumbers.size();
        // Everything before position i is already sorted; insert record i into its correct place
        for (int i = 1; i < n; i++) {
            int j = i;
            // Move the record left while the one before it has a larger request number
            while (j > 0 && requestNumbers.get(j - 1) > requestNumbers.get(j)) {
                swapRequests(j, j - 1);
                j--;
            }
        }
    }