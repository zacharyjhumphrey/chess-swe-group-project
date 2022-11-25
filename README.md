# chess-swe-group-project

## Team Roles
Team Leader - Zachary Humphrey

Backend Owner - Cole McCauley

Frontend Owner - Seth DeWalt

Database Owner - Miguel

## Running the code
ocsf.jar and mysql-connector-java-5.1.40-bin.jar jar are expected to be in the root directory of the project. Without these here, the class path of the project (inside of the .bat files) won't work

## Contribution Process
1. Pull the code down to your machine
`git clone https://github.com/zacharyjhumphrey/chess-swe-group-project.git`

2. Create a branch off the main branch for development of ticket
`git checkout -b <your name here>/CHESS-<ticket number>`

3. Make your changes to fulfill ticket requirements

4. Incrementally be adding and commiting your changes
`git add .`
`git commit -m "<describe commit here>"`

5. Once you have completed development on the ticket, you will need to pull changes that have been made to the main branch into your local main branch and then rebase your branch into main
`git checkout main`
`git pull`
`git checkout <your branch here>`
`git rebase main`

6. Push your changes to your branch
`git push -f`

7. Go to GitHub website, create a request to merge your branch into main. Make sure the description of the pull request has the ticket number. Message the discord that there is a pull request ready for review

8. Wait for the rest of the team to approve/comment on the pull request

9. Once the pull request has two approvals, mark the pull request as ready to merge. Zach will merge the code into main
