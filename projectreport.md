Cyber Security Base Course Project I report
Project can be found at https://github.com/towv/nocybersecurity

Disclaimer: It is not pretty and it is not secure.
The web application is very simple and provides functionality for adding tasks, registering, logging in, removing and editing users. The project was done using SpringBoot and H2 database. The purpose was to create a vulnerable application. It purposefully has several of the OWASP top 10 vulnerabilities.

Below is a description of 5 different vulnaribities, how to reproduce them and how to fix them:

Issue: Missing Function Level Access Control
Steps to reproduce:
1. Start the app with the method of your choosing.
2. Go to http://localhost:8080 (by default the port is 8080, this can be configured)
3. Click register
4. Create a new account by writing an username and a password and clicking register (it has to be new but if you just started the application it is because the database is only saved in memory)
5. Log in with your new account (clicking register redirects to login page)
6. Open devtools (element inspector) by pressing F12
7. From inspector find hidden ”a href”-element and make it visible by double clicking ’hidden=”hidden”’ and pressing delete. When you have deleted that text press enter.
8. Click the revealed link that says ”Clear database”.
9. You have cleared the database of all data.

Fix for the issue: Hidden -elements is not a good way to hide stuff. They are not really all that hidden. Especially you should not have such important functionality behind a hidden element. For moderating there should be proper authentication or possibly a completely seperate moderation environment with proper security.

Issue: Sensitive Data Exposure
Steps to reproduce:
1. Start the app with the method of your choosing.
2. Go to http://localhost:8080 (by default the port is 8080, this can be configured)
3. Click register
4. Create a new account by writing an username and a password and clicking register (it has to be new but if you just started the application it is because the database is only saved in memory)
5. Go to http://localhost:8080/moderator (or the port you configured)
6. You will see all usernames and passwords that have been stored in the database in plain text.
7. Pick an account and use it to log in.

Fix for the issue: All passwords should be encrypted and never saved as plain text. There are encryption libraries such as Bcrypt. The password should be encrypted before saving. In addition you should use for example SpringBoot security configuration to set access checking for every site.
This goes especially for such site meant for moderators that lists all users and passwords. Also do not make such site that lists users and passwords.


Issue: Broken authentication and session management
Steps to reproduce:
1. Start the app with the method of your choosing.
2. Go to http://localhost:8080 (by default the port is 8080, this can be configured)
3. Click register
4. Create a new account by writing an username and a password and clicking register (it has to be new but if you just started the application it is because the database is only saved in memory)
5. Log in with your new account (clicking register redirects to login page)
6. Click ”Modify account!
7. Change the id in address http://localhost:8080/ (id) /modifyAccount manually to say 1.
8. Give a new username
9. Give a new password
10. Click ”Edit account!”
11. You have now changed the username and password of the user with id 1.

Fix for the issue: Editing account should be made more private. Before changing username and password you could ask for the old password. You should also check that the user has access rights for the site. You should never use the user id as a path variable.


Issue: Insecure Direct Object References
Steps to reproduce:
1. Start the app with the method of your choosing.
2. Go to http://localhost:8080 (by default the port is 8080, this can be configured)
3. Click register
4. Create a new account by writing an username and a password and clicking register (it has to be new but if you just started the application it is because the database is only saved in memory)
5. Log in with your new account (clicking register redirects to login page)
6. You would see your own tasks if you had any (To add tasks you can click ”Add tasks!”, on the opening page, write the task and click ”Add”). You can manually modify the address to http://localhost:8080/tasks/1
7. You will see tasks of the user with id 1.

Fix for the issue: Never use user id as a path variable. Instead you  should use authorized indirect parameters in order to identify the user whose tasks are shown. In addition you should use for example SpringBoot security configuration to set access checking for every site.


Issue: Security misconfiguration
Steps to reproduce:
1. Start the application
2. Go to http://localhost:8080 (by default the port is 8080, this can be configured)
3. Click register
4. Return to previous page
5. Type “moderator” to the username
6. Type “moderator” to the password
7. Click login
7. You are now logged in as the moderator.

Fix for the issue: Never use such username/password combination. This goes especially for moderator. Actually you should have a seperate environment for development. The moderators should also not share an account.


This lists is not exhaustive and does not list all security problems with the said application. To sum up: Use proper authentication, restrict access to pages, use encryption, no id as pathvariable.
