<h1>Definition</h1>
<p>ExamBuilder is a platform based on Spring Boot framework that contains Admins,Instructors and Students, Where instructors could create exams with bank of questions and answers ,
Then each student assigned to a specifiec exam will have his own copy of questions and answers that been picked randomly from questions bank,
and each exam has its end countdown timer where the student should answer all the questions before the exam timed out!</p>


![Untitled](https://user-images.githubusercontent.com/74168043/113866240-c1216c80-97b5-11eb-8620-f9f66ea0f4bf.png)

<h1>Technologies used:</h1>
<p>- Front-End : JSP,HTML,CSS,Bootstrap,JS,AJAX,JQUERY</p>
<p>- Back-End : Spring Boot</p>
<p>- Database : MySQL</p>


<h2>Admin</h2>

Admin could review all users with their emails and names :
![Desktop Screenshot 2021 04 06 - 21 28 07 49](https://user-images.githubusercontent.com/74168043/113844215-ed7cbf00-979c-11eb-8428-bcfaa10a1a99.png)
![Desktop Screenshot 2021 04 06 - 21 28 17 43](https://user-images.githubusercontent.com/74168043/113844235-f1a8dc80-979c-11eb-9fda-a66941db2c73.png)
![Desktop Screenshot 2021 04 06 - 21 28 28 49](https://user-images.githubusercontent.com/74168043/113844248-f40b3680-979c-11eb-8b88-0520b891575b.png)

Admin could delete , add, edit users , except himself...

Edit user:
![Desktop Screenshot 2021 04 07 - 12 32 39 09](https://user-images.githubusercontent.com/74168043/113844692-6f6ce800-979d-11eb-8321-5da12c390261.png)

Add User :
![Desktop Screenshot 2021 04 06 - 22 57 06 60](https://user-images.githubusercontent.com/74168043/113844834-94f9f180-979d-11eb-9452-3283632f4430.png)

 Included validations :
- Password must be greater than 8 chars.
- Password and its confirm must match.
- First and Last name must be at least 3 chars long.
- Email shouldn't be used from another user.
- Email should be in a valid format as email@example.com

<h2>Instructor</h2>

Instructor could review all students names and emails:
![Desktop Screenshot 2021 04 06 - 22 56 29 44](https://user-images.githubusercontent.com/74168043/113845779-86600a00-979e-11eb-930f-1ab6532a821c.png)

Instructor could add,view,delete and edit his exams:
![Desktop Screenshot 2021 04 06 - 23 30 34 14](https://user-images.githubusercontent.com/74168043/113847322-089cfe00-97a0-11eb-91f4-e6aa8cf2c8e3.png)

Create Exam form validtions :
 - Title should be between 2 and 50 characters long.
 - Mark should be between 5 and 100 marks.
 - Duration should be between 5 and 120 minutes.
 - Exam date should be in the future.

- Instructor could create yes or no and multiple choice questions.
- Instructor could add multiple answers for each questions and check the correct answers.
- Instructor could add or remove students from the exam.
- Instructor could edit the exam if it hasn't started.
Exam Settings page:
![Desktop Screenshot 2021 04 06 - 23 31 10 44](https://user-images.githubusercontent.com/74168043/113850087-c6c18700-97a2-11eb-8706-db75bf821dd4.png)

Multiple choices question:
![Desktop Screenshot 2021 04 07 - 01 25 20 48](https://user-images.githubusercontent.com/74168043/113853607-8401ae00-97a6-11eb-9d72-3b119f18a985.png)

Yes/No Question :
![Desktop Screenshot 2021 04 07 - 01 25 37 80](https://user-images.githubusercontent.com/74168043/113853760-b57a7980-97a6-11eb-984c-a9ef5dc9f87c.png)

Add/Remove students from the exam:
![Desktop Screenshot 2021 04 07 - 11 35 08 13](https://user-images.githubusercontent.com/74168043/113854011-07230400-97a7-11eb-8557-3b0928c915a0.png)

<pre>After the instructor filled the exam questions and answers he could publish the exam so it could be shown by students</pre>
![Desktop Screenshot 2021 04 07 - 13 52 38 00](https://user-images.githubusercontent.com/74168043/113855301-8d8c1580-97a8-11eb-83d8-22ed095fd53b.png)
Publish Exam validations:
- Create Exam validations included.
- Exam should have at least 5 questions.
- Yes/No question should have 2 answers one of them is correct.
- Multiple choices question should have at least 3 wrong answers and 1 correct.

Instructor could see all the extra exams in the website :
![Desktop Screenshot 2021 04 07 - 13 49 10 43](https://user-images.githubusercontent.com/74168043/113854977-28d0bb00-97a8-11eb-93b5-4fdf1c187985.png)

<h2>Student</h2>

Student could view the published exams that been added to it.
 * If the exam is today , the start countdown timer will appear
 * If the exam started , the end countdown timer will appear
![Desktop Screenshot 2021 04 07 - 14 25 49 36](https://user-images.githubusercontent.com/74168043/113859150-40f70900-97ad-11eb-8b9b-e03116e5bca1.png)
![Desktop Screenshot 2021 04 07 - 14 21 13 06](https://user-images.githubusercontent.com/74168043/113859168-448a9000-97ad-11eb-911e-232cce0eeb93.png)
* After the exam started the student will have the permission to join the exam and answering questions unitl the exam timeout .. 
![Desktop Screenshot 2021 04 07 - 11 56 25 10](https://user-images.githubusercontent.com/74168043/113859519-aa771780-97ad-11eb-8442-b4d18460a352.png)
* After the student answering all the questions , the submit button wil appear .
![Desktop Screenshot 2021 04 07 - 11 57 42 78](https://user-images.githubusercontent.com/74168043/113859703-e316f100-97ad-11eb-928d-3db6d9a01d6e.png)

* After submitting the exam , the exam will apear in the results table with the student mark .
![Desktop Screenshot 2021 04 07 - 14 35 01 16](https://user-images.githubusercontent.com/74168043/113860266-81a35200-97ae-11eb-8e56-63e10b500f0f.png)

Student could add or withdraw from extra exams without needed to be added by the instructor.

![Desktop Screenshot 2021 04 07 - 01 36 16 79](https://user-images.githubusercontent.com/74168043/113860716-05f5d500-97af-11eb-8d95-3b8625bd1ae3.png)
![Desktop Screenshot 2021 04 07 - 14 35 01 16](https://user-images.githubusercontent.com/74168043/113860763-15751e00-97af-11eb-929b-8ea1e3492b8b.png)






