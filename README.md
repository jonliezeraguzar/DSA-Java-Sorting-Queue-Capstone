1. Project Title: Library Borrowing Request System
   

3. Project Description
   
In a library, many students can ask to borrow books at the same time. Without a system, it is hard to know who asked first, which books are still available, and which requests are already done.
The Library Borrowing Request System is a console-based Java program that helps with this. It keeps a list of books and lets students' borrowing requests be registered. Each request goes into a queue so the student who asked first is served first (First-In, First-Out). When a request is processed, the program checks if a copy is available. If there is one, the request is approved and the number of copies goes down by one. If not, the request is rejected. The program can also sort the book records and the borrowing records.
The program is written in Java without OOP. It has no custom classes or objects. It only uses static methods, ArrayLists and a built-in Queue.

3. Objectives
   
Handle borrowing requests in the order they arrive using a queue (FIFO).

Store and manage book and request records using ArrayLists.

Sort records using sorting algorithms written by hand, without Java's built-in sort methods.

Provide a menu-driven console program that is easy to use.

Handle invalid input without the program crashing.

Follow the procedural (non-OOP) approach.

4. Use Cases and Features
   
The program has two kinds of users: the student, who asks to borrow a book, and the librarian, who manages the books and processes the requests.

Book records:

Option 1: Add a book (title, author, year, number of copies)

Option 2: Display all books

Option 3: Search a book by title or author

Option 4: Update the number of copies of a book

Option 5: Remove a book (not allowed if it still has pending requests)

Option 6: Sort books by title or by publication year

Borrowing requests:

Option 7: Register a borrowing request and add it to the queue

Option 8: Process the next request (approve or reject it)

Option 9: Peek at the next request without removing it

Option 10: Display all pending requests

Option 11: Check if the queue is empty

Option 12: Display all borrowing records and their status

Option 13: Sort borrowing records by student name or by request number

Option 14: Cancel a pending request

Option 0: Exit

Other features:

Invalid input such as letters, empty text or numbers out of range is rejected and the program asks again.

Messages are shown when the queue is empty, a book is not found, or an action cannot be done.

Seven sample books are loaded when the program starts: The Five People You Meet in Heaven, Demian, They Both Die at the End, Have a Little Faith, To Kill a Mockingbird, Atomic Habits and Noli Me Tangere.

5. Data Structure Explanation
   
ArrayLists

Records are stored in parallel ArrayLists. Each list holds one field, and the same index in every list belongs to the same record. For example, index 1 in all the book lists describes the same book (Demian, by Herman Hesse, published in 1919, with 1 copy).

Book lists:

bookIds: the ID of each book

bookTitles: the title of each book

bookAuthors: the author of each book

bookYears: the publication year of each book

bookCopies: the number of copies available

Request lists:

requestNumbers: the number of each request

requestStudentIds: the student ID of the requester

requestStudentNames: the name of the requester

requestBookIds: the ID of the requested book

requestStatuses: Pending, Approved, Rejected or Cancelled

To keep the lists aligned, adding a record adds to every list, removing a record removes the same index from every list, and sorting swaps a whole record at once using the swapBooks and swapRequests methods.

Queue

Pending requests are kept in a Queue of Integers created with a LinkedList:

static Queue<Integer> pendingQueue = new LinkedList<>();

The queue stores request numbers. This matters because sorting the borrowing records changes their positions in the ArrayLists, but the queue order stays the same since it does not depend on those positions.

How the queue is used:

add(): registering a request puts it at the back of the line.

poll(): processing the next request takes the one at the front.

peek(): looks at the front request without removing it.

A loop over the queue is used to display the pending requests.

isEmpty(): checks if the queue is empty before every queue operation.

remove(): used to cancel a specific request.

The queue is the center of the main workflow. Every request enters the queue when it is registered, and requests are handled strictly in the order they arrived.

6. Sorting Algorithm Explanation
   
Bubble Sort is used to sort books by title (A to Z). It compares two neighboring titles and swaps them if they are in the wrong order, and it repeats this until the list is sorted. The program stops early if a full pass makes no swaps. It was chosen because the catalog is small and the algorithm is simple.

Insertion Sort is used to sort books by publication year (oldest to newest) and borrowing records by request number (smallest to largest). It takes one record at a time and moves it left until it is in the correct place. It was chosen because these lists are usually already almost in order, and Insertion Sort is fast in that case.

Selection Sort is used to sort borrowing records by student name (A to Z). It finds the smallest name in the unsorted part of the list and swaps it into the next position. It was chosen because it makes very few swaps, and each swap moves a whole record across five ArrayLists.

7. How to Run the Program
   
To run the program directly, open a terminal in the folder that has Aguzar_LibraryBorrowingSystem.java and run:

java Aguzar_LibraryBorrowingSystem.java

Or compile it first and then run it:

javac Aguzar_LibraryBorrowingSystem.java

java Aguzar_LibraryBorrowingSystem


8. Screenshots of the System
Screenshot 1: Main menu

Main menu

The main menu shows all the operations for book records and borrowing requests.
<img width="1006" height="834" alt="menu" src="https://github.com/user-attachments/assets/e5bd96d2-2e7c-4d34-a466-8084074d3ea7" />

Screenshot 2: Adding a record and displaying stored records

Add and display books

A new book is added by entering its title, author, publication year and number of copies. The list of stored books is then displayed, including the new book.

<img width="760" height="314" alt="add book" src="https://github.com/user-attachments/assets/147af624-0f4a-4bc7-91b7-264897e4d35c" />
<img width="1464" height="478" alt="book records" src="https://github.com/user-attachments/assets/650974a4-359f-4a10-a2f1-bc7ba4562945" />

Screenshot 3: Enqueue

Enqueue

Registering a borrowing request adds it to the back of the queue.

<img width="1412" height="674" alt="registermigs" src="https://github.com/user-attachments/assets/f7db1b41-b1f4-4496-85c3-d2fc8dabddb0" />

Screenshot 4: Displaying the queue

Display queue

All pending requests are shown from the front of the queue to the back.

<img width="1434" height="346" alt="4  quoe" src="https://github.com/user-attachments/assets/b14d5a19-d7bf-4479-bd10-49e5ae4c537a" />

Screenshot 5: Peek

Peek

The request at the front of the queue is shown without removing it.

<img width="722" height="234" alt="5 peek" src="https://github.com/user-attachments/assets/31b2b670-edb8-4d60-be52-10fff35593fc" />

Screenshot 6: Dequeue

Dequeue

The request at the front of the queue is processed and removed. It is then approved or rejected depending on the available copies.

<img width="1222" height="296" alt="6 deq" src="https://github.com/user-attachments/assets/67cd2fff-f4ae-4c49-9bec-d5bdd840fe9c" />

Screenshot 7: FIFO behavior

FIFO

Three requests were registered in order and then processed in the same order. The first request added was the first one processed.

Alex was served first, then Raizen, with Miguel still waiting.

<img width="1260" height="288" alt="for 7" src="https://github.com/user-attachments/assets/9e442888-841a-40da-bc33-b073034a914d" />
<img width="1046" height="282" alt="for72" src="https://github.com/user-attachments/assets/4da4caef-5493-41b1-a9a1-848a24399503" />
<img width="1418" height="310" alt="for73" src="https://github.com/user-attachments/assets/8d66126b-6ada-4b2e-bb58-f2851a9ff0ab" />

Screenshot 8: First sorting algorithm, Bubble Sort (books by title)

Bubble Sort

The book records before and after sorting by title from A to Z using Bubble Sort.

<img width="1378" height="1052" alt="8" src="https://github.com/user-attachments/assets/615a884d-c4dc-4a81-b904-a42d2816156e" />
<img width="1306" height="176" alt="ext" src="https://github.com/user-attachments/assets/13658b0b-c7d9-4414-af69-a5ed7bcf53ea" />

Screenshot 9: Second sorting algorithm, Insertion Sort (books by publication year)

Insertion Sort

The book records before and after sorting by publication year from oldest to newest using Insertion Sort.

<img width="1398" height="956" alt="9 insertiom" src="https://github.com/user-attachments/assets/d0564e5e-3f2b-4090-881f-68f7f15e92e5" />

Screenshot 10: Input validation and error messages

Input validation

Invalid input, such as letters or numbers outside the menu range, is rejected with an error message and the program asks again.
<img width="772" height="206" alt="invalid" src="https://github.com/user-attachments/assets/627e782c-4f0b-4077-a955-efdbb5ea6b98" />

Screenshot 11: Empty queue message

Empty queue message

<img width="668" height="190" alt="last9" src="https://github.com/user-attachments/assets/3d36f6c5-9bcc-46d1-bd61-5f8f0019a1d9" />
<img width="718" height="194" alt="last 10" src="https://github.com/user-attachments/assets/ec175b98-699f-40d1-a17b-50bfec9ed4c9" />


Processing, peeking at or displaying an empty queue shows a clear message instead of crashing.

