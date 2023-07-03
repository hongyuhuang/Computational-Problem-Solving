#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LENGTH 100

struct Client
{
    char *firstName;
    char *lastName;
    char *phoneNumber;
    char *emailAddress;
};

static int i, numberOfClients;

// Find clients by first name
void findFirstName(struct Client **clients, char *s)
{
    bool found = false;
    for (i = 0; i < numberOfClients; i++)
    {
        if (strcasecmp(clients[i]->firstName, s) == 0)
        {
            found = true;
            printf("Client found!\n----------\nName: %s %s\nEmail: %s\nPhone Number: %s\n----------\n", clients[i]->firstName, clients[i]->lastName, clients[i]->emailAddress, clients[i]->phoneNumber);
        }
    }
    if(found == false) printf("%s not found.\n", s);
}

// Find clients by last name
void findLastName(struct Client **clients, char *s)
{
    bool found = false;
    for (i = 0; i < numberOfClients; i++)
    {
        if (strcasecmp(clients[i]->lastName, s) == 0)
        {
            found = true;
            printf("Client found!\n----------\nName: %s %s\nEmail: %s\nPhone Number: %s\n----------\n", clients[i]->firstName, clients[i]->lastName, clients[i]->emailAddress, clients[i]->phoneNumber);
        }
    }
    if(found == false) printf("%s not found.\n", s);
}

// Find clients by phone number
void findPhoneNumber(struct Client **clients, char *s)
{
    bool found = false;
    for (i = 0; i < numberOfClients; i++)
    {
        if (strcmp(clients[i]->phoneNumber, s) == 0)
        {
            found = true;
            printf("Client found!\n----------\nName: %s %s\nEmail: %s\nPhone Number: %s\n----------\n", clients[i]->firstName, clients[i]->lastName, clients[i]->emailAddress, clients[i]->phoneNumber);
        }
    }
    if(found == false) printf("%s not found.\n", s);
}

// Find clients by email address
void findEmailAddress(struct Client **clients, char *s)
{
    bool found = false;
    for (i = 0; i < numberOfClients; i++)
    {
        if (strcasecmp(clients[i]->emailAddress, s) == 0)
        {
            found = true;
            printf("Client found!\n----------\nName: %s %s\nEmail: %s\nPhone Number: %s\n----------\n", clients[i]->firstName, clients[i]->lastName, clients[i]->emailAddress, clients[i]->phoneNumber);
        }
    }
    if(found == false) printf("%s not found.\n", s);
}

void displayClients(struct Client **clients, int numberOfClients)
{
    // Iterate over all clients
    int i;
    for (i = 0; i < numberOfClients; i++)
    {
        // Print the client's information
        printf("----------\nName: %s %s\nEmail: %s\nPhone Number: %s\n", clients[i]->firstName, clients[i]->lastName, clients[i]->emailAddress, clients[i]->phoneNumber);
    }
    printf("----------\n");
}

int main(int argc, char **argv)
{
    char fileName[MAX_LENGTH];
    FILE *file = NULL;

    // Use the file name from the command-line argument
    if (argc >= 2)
    {
        file = fopen(argv[1], "r");
        if (file == NULL)
        {
            printf("File not found from command line argument\n");
        }
    }

    bool validFile = (file != NULL);

    // Prompt the user for the file name until a valid file is entered
    while (!validFile)
    {
        printf("Enter the file name for the client data: ");
        scanf("%s", fileName);
        file = fopen(fileName, "r");
        if (file == NULL)
        {
            printf("File not found. Please try again.\n");
        }
        else
        {
            printf("File found.\n");
            validFile = true;
        }
    }

    char line[MAX_LENGTH];
    struct Client **clients = malloc(100 * sizeof(struct Client *));

    while (fgets(line, sizeof(line), file) != NULL) // Read the file line by line
    {
        // Allocate memory for the client
        clients[numberOfClients] = malloc(sizeof(struct Client));
        clients[numberOfClients]->firstName = malloc(80 * sizeof(char));
        clients[numberOfClients]->lastName = malloc(80 * sizeof(char));
        clients[numberOfClients]->phoneNumber = malloc(80 * sizeof(char));
        clients[numberOfClients]->emailAddress = malloc(80 * sizeof(char));

        // Parse the values from the line using sscanf()
        sscanf(line, "%s %s %s %s", clients[numberOfClients]->firstName, clients[numberOfClients]->lastName, clients[numberOfClients]->phoneNumber, clients[numberOfClients]->emailAddress);

        // Increment the number of clients
        numberOfClients++;
    }
    // Close the file
    fclose(file);

    // Display the number of clients loaded from the file
    printf("Welcome! %d clients loaded from the file.\n", numberOfClients);

    // Display the menu and prompt the user for a command
    char input;
    char searchParameter[MAX_LENGTH];
    do
    {
        printf("This program provides the following functions::\n 1. Search by first name\n 2. Search by last name\n 3. Search by phone number\n 4. Search by email\n 5. Display all clients\n q. Quit\nSelect task : ");
        scanf(" %c", &input);
        switch (input)
        {
        case '1':
            printf("Enter the first name: ");
            scanf("%s", searchParameter);
            findFirstName(clients, searchParameter);
            break;
        case '2':
            printf("Enter the last name: ");
            scanf("%s", searchParameter);
            findLastName(clients, searchParameter);
            break;
        case '3':
            printf("Enter the phone number: ");
            scanf("%s", searchParameter);
            findPhoneNumber(clients, searchParameter);
            break;
        case '4':
            printf("Enter the email address: ");
            scanf("%s", searchParameter);
            findEmailAddress(clients, searchParameter);
            break;
        case '5':
            printf("Displaying all clients:\n");
            displayClients(clients, numberOfClients);
            break;
        case 'q':
            printf("Quitting...\n");
            break;
        default:
            printf("Invalid command. Try again.\n");
            break;
        }
    } while (input != 'q');

    //  Iterate over all clients annd free the malloc-ed memory space.
    for (i = 0; i < numberOfClients; i++)
    {
        // Free the memory allocated for the client
        free(clients[i]->firstName);
        free(clients[i]->lastName);
        free(clients[i]->emailAddress);
        free(clients[i]->phoneNumber);
        free(clients[i]);
    }
    // Free the memory allocated for the clients array
    free(clients);

    return 0;
}
