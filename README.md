# ReadMe
### Basic Assumptions:
1. All commands only accept one space between each argument
2. *Show Number*: positive integer only
3. *Number of Rows*: integer, 1-26
4. *Number of seats per row*: integer, 1-10
5. *Cancellation window in minutes*: positive integer only
6. *Phone#*: normal String,
7. All arguments do not contain space & escape character
8. Use "Exit" to quit the system
9. The use of System.out is preferred for outputting to console, rather than log.info or log.error.

### Command Details

#### Set
1. Once the show has been set, modifications to the show are not permitted.

#### View 
1. if the *Show Number* is less than 1, it will list all shows
2. if the *Show Number* is valid, it will display the ticket details and seat availability.

#### Book
1. user can rebook if s/he cancelled the ticket of the show
2. If booking of any one seat fails, no seats will be booked and a ticket will not be generated.
3. The system only allows a single booking action to take place at a time, and therefore seat reservation logic has not been implemented.

#### Exit
1. exit the program
