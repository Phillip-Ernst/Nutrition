package Control;

/**
 * This class validates user input to prevent injection attacks and general user errors
 *
 * @author Isaac Hotop
 */
public class InputValidation {
    char[] badChars = {'\'', '"', ';', '`', '-', '_', '(', ')', '[', ']', '*', '=', '/', '\\', '%', '>', '<', '$', '&', '|'};
    char[] badFoodChars = {'\'', '"', ';', '`', '-', '_', '[', ']', '*', '=', '/', '\\', '%', '>', '<', '$', '&', '|'};

    /**
     * UsernamePasswordValidation
     * checks for certain special characters and string length to prevent against injection attacks
     *
     * @param username the user's username
     * @param password the user's password
     * @return true if username and password is valid or false if not valid
     */
    public boolean UsernamePasswordValidation(String username, String password) {
        //Checks for string length and if it contains any characters that could be used in injection attacks
        if(username.length() <= 20 && password.length() <= 20) {

            for (char uc : username.toCharArray()) {
                for (char bc : badChars) {
                    if (uc == bc) {
                        //username contains a bad character; failed
                        return false;
                    }
                }
            }
            for (char pc : password.toCharArray()) {
                for (char bc : badChars) {
                    if (pc == bc) {
                        //password contains a bad character; failed
                        return false;
                    }
                }
            }
            //username and password passed all the checks
            return true;
        }
        return false;
    }

    /**
     * foodInputValidation
     * checks for certain special characters and string length to protect against injection attacks
     *
     * @param food the food that the user entered
     * @return true if the food is valid, false if not
     */
    public boolean foodInputValidation(String food) {
        if(food.length() <= 25) {
            for (char fc : food.toCharArray()) {
                for (char bc : badFoodChars) {
                    //returns true if they are not equal (passed), returns false if they are equal (failed)
                    if (fc == bc) {
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            //food name is too long; failed
            return false;
        }
    }
}
