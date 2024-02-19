#include <sys/stat.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>

void createFolder() {
    // Declare a character array to store folder name
    char folderName[50];
    printf("Enter Folder name: ");
    scanf("%s", folderName);

    // 0777 sets the permissions
    if (mkdir(folderName, 0777) == 0) { //It provides full access to everyone.
        // If the folder creation is successful, print a message
        printf("Folder '%s' created.\n", folderName);
    } else {
        // If there's an error creating the folder, print an error message
        printf("Error creating folder.\n");
    }
}

void createFile() {
    char fileName[50];
    printf("Enter File Name: ");
    scanf("%s", fileName);
// Create an empty file using the touch command
    char command[100];
    sprintf(command, "touch %s", fileName);

    if (system(command) == 0) { //the system call used to set permissions on a file is typically chmod()
        printf("File '%s' created.\n", fileName);
    } else {
        printf("Error creating file.\n");
    }
}

void changeFileRights() {
    char fileName[50];
//Declares an integer variable named permissions to store the new file permissions.
    int permissions;

    printf("Enter File Name: ");
    scanf("%s", fileName);

    printf("Enter new permissions (in octal): ");
    scanf("%o", &permissions);

//Uses the chmod system call to change the permissions of the file
    if (chmod(fileName, permissions) == 0) {
// If the operation is successful (returns 0), 
        printf("Permissions for file '%s' changed to %o.\n", fileName, permissions);
    } else {
// if else then it prints an error message indicating that there was an error changing file permissions.
        printf("Error changing file permissions.\n");
    }
}

void searchFile() {
//Declares a character array name to store the name of the file.
    char fileName[50];
    printf("Enter File Name to search: ");
    scanf("%s", fileName);

//This structure will be used to store information about the file when using the stat system call.
    struct stat fileInfo;
//Uses the stat system call to retrieve information about the file 
    if (stat(fileName, &fileInfo) == 0) {
        printf("File '%s' found.\n", fileName);
    } else {
//
        printf("File '%s' not found.\n", fileName);
    }
}


void createUser() {
    char username[50];
    printf("Enter Username: ");
    scanf("%s", username);

    // Use useradd to create the user
    char createUserCommand[100];
    sprintf(createUserCommand, "sudo useradd %s", username);

    if (system(createUserCommand) != 0) {
        perror("Error creating user");
        return;
    }

    // Set the password for the user
    char setPasswordCommand[100];
    sprintf(setPasswordCommand, "echo '%s:%s' | sudo chpasswd", username, "new_password");

    if (system(setPasswordCommand) == 0) {
        printf("User '%s' created with password.\n", username);
    } else {
        perror("Error setting password");
        // You might want to handle errors appropriately
    }
}

void deleteUser() {
    char username[50];
    printf("Enter Username to delete: ");
    scanf("%s", username);

    // Useing userdel command 
    char command[100];
    sprintf(command, "sudo userdel %s", username);

    if (system(command) == 0) {
        printf("User '%s' deleted.\n", username);
    } else {
        printf("Error deleting user.\n");
    }
}

void updateUser() {
    char username[50];
    printf("Enter Username to update: ");
    scanf("%s", username);

    // Using usermod command
    char command[200];
    sprintf(command, "sudo usermod -l %s %s", username, username);

    // Print the constructed command for debugging
    printf("Executing command: %s\n", command);

    int returnValue = system(command);

    // Print the return value of system for debugging
    printf("Return value: %d\n", returnValue);

    if (returnValue == 0) {
        printf("User '%s' updated.\n", username);
    } else {
        printf("Error updating user.\n");
        // Print additional error information
        perror("system");
    }
}

void switchUser() {
    char username[50];
    printf("Enter Username to switch: ");
    scanf("%s", username);

    // Using su command
    char command[100];
    sprintf(command, "su %s", username);

    if (system(command) == 0) {
        printf("Switched to user '%s'.\n", username);
    } else {
        printf("Error switching user.\n");
    }
}
void killProcess() {
    pid_t pid;
    printf("Enter the PID of the process to kill: ");
    scanf("%d", &pid);

    if (kill(pid, SIGKILL) == 0) { // If the kill call returns zero, it means the signal was sent successfully
        printf("Process with PID %d killed.\n", pid);
    } else {
        perror("Error killing process"); //print a description for the last encountered error,
    }
}

void createProcess() {
    pid_t child_pid;
    child_pid = fork();

    if (child_pid == 0) {
        // Code inside this block will be executed by the child process
        printf("Child process is running (PID: %d).\n", getpid());
        // Add your specific child process logic here

        // Exit the child process
        exit(0);
    } else if (child_pid > 0) {
        // Code inside this block will be executed by the parent process
        printf("Parent process created a child with PID %d.\n", child_pid);
    } else {
        perror("Error creating process");
    }
}

void listProcesses() {
    printf("List of currently running processes:\n");

    // Use system command to execute "ps" command and list processes
    system("ps aux");
}
void sortArray(int arr[], int n) {
    // Sorting array in ascending order
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                // swap arr[j] and arr[j+1]
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }

    // Displaying sorted array
    printf("Sorted Array: ");
    for (int i = 0; i < n; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}
void runProgram() {
    char command[100];
    printf("Enter the command or program to run: ");
    scanf("%s", command);

    if (system(command) == 0) {
        printf("Program '%s' executed.\n", command);
    } else {
        printf("Error executing program.\n");
    }
}
int main() {
    int mainChoice, subChoice;

    do {
        printf("\nMain Menu:\n");
        printf("1. User Management\n");
        printf("2. File Management\n");
        printf("3. Process Management\n");
        printf("0. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &mainChoice);

        switch (mainChoice) {
            case 1:
                // User Management
                do {
                    printf("\nUser Management Menu:\n");
                    printf("1. Create User\n");
                    printf("2. Delete User\n");
                    printf("3. Update User\n");
                    printf("4. Switch User\n");
                    printf("0. Back to Main Menu\n");
                    printf("Enter your choice: ");
                    scanf("%d", &subChoice);

                    switch (subChoice) {
                        case 1:
                            createUser();
                            break;
                        case 2:
                            deleteUser();
                            break;
                        case 3:
                            updateUser();
                            break;
                        case 4:
                            switchUser();
                            break;
                        case 0:
                            printf("Returning to Main Menu...\n");
                            break;
                        default:
                            printf("Invalid choice. Please try again.\n");
                    }
                } while (subChoice != 0);
                break;

            case 2:
            // File Management
                do {
                    printf("\nFile Management Menu:\n");
                    printf("1. Create Folder\n");
                    printf("2. Create File\n");
                    printf("3. Change file rights\n");
                    printf("4. Search file\n");
                    printf("0. Back to Main Menu\n");
                    printf("Enter your choice: ");
                    scanf("%d", &subChoice);

                    switch (subChoice) {
                        case 1:
                            createFolder();
                            break;
                        case 2:
                            createFile();
                            break;
                        case 3:
                            changeFileRights();
                            break;
                        case 4:
                            searchFile();
                            break;
                        case 0:
                            printf("Returning to Main Menu...\n");
                            break;
                        default:
                            printf("Invalid choice. Please try again.\n");
                    }
                } while (subChoice != 0);
                break;

            case 3:
                // Process Management
                do {
                    printf("\nProcess Management Menu:\n");
                    printf("1. Kill Process\n");
                    printf("2. Create Process\n");
                    printf("3. List Processes\n");
                    printf("4. Sort an array\n");
                    printf("5. Run Program\n");
                    printf("0. Back to Main Menu\n");
                    printf("Enter your choice: ");
                    scanf("%d", &subChoice);

                    switch (subChoice) {
                        case 1:
                            killProcess();
                            break;
                        case 2:
                            createProcess();
                            break;
                        case 3:
                            listProcesses();
                            break;
                        case 4:
                    // Sort Array
                    {   int size;
                        printf("Enter the size of the array: ");
                        scanf("%d", &size);

                        int arr[size];
                        printf("Enter the elements of the array:\n");
                        for (int i = 0; i < size; i++) {
                            scanf("%d", &arr[i]);
                        }

                        sortArray(arr, size);
                    }
                             break;
                        case 5:
                            runProgram();
                        case 0:
                            printf("Returning to Main Menu...\n");
                            break;
                        default:
                            printf("Invalid choice. Please try again.\n");
                    }
                } while (subChoice != 0);
                break;

            case 0:
                printf("Exiting...\n");
                break;

            default:
                printf("Invalid choice. Please try again.\n");
        }
    } while (mainChoice != 0);

    return 0;
}
