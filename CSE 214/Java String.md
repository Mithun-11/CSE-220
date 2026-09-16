Below is a practical tutorial on the Java `String` operations you are most likely to need in exams and projects.

## 1. Creating strings

```java
String name = "Ishrat";
String empty = "";
String another = new String("Hello"); // Valid, but normally unnecessary
```

Usually, use string literals:

```java
String text = "Hello";
```

## 2. Strings are immutable

A `String` object cannot be changed after creation.

```java
String text = "hello";
text.toUpperCase();

System.out.println(text); // hello
```

`toUpperCase()` produces a new string. It does not modify `text`.

```java
text = text.toUpperCase();

System.out.println(text); // HELLO
```

This rule applies to methods such as:

- `toUpperCase()`
    
- `toLowerCase()`
    
- `replace()`
    
- `substring()`
    
- `trim()`
    

You must save the returned string if you want to keep the change.

---

# Basic information methods

## 3. `length()`

Returns the number of characters.

```java
String text = "Hello";

System.out.println(text.length()); // 5
```

Valid indexes go from:

```text
0 to length() - 1
```

For `"Hello"`:

```text
Index:      0 1 2 3 4
Character:  H e l l o
```

Notice:

```java
array.length       // Array: field
text.length()      // String: method
list.size()        // List: method
```

## 4. `isEmpty()`

Checks whether the length is zero.

```java
String a = "";
String b = " ";

System.out.println(a.isEmpty()); // true
System.out.println(b.isEmpty()); // false
```

A space is still a character.

## 5. `isBlank()`

Checks whether a string is empty or contains only whitespace.

```java
System.out.println("".isBlank());      // true
System.out.println("   ".isBlank());   // true
System.out.println("\n\t".isBlank());  // true
System.out.println("Hello".isBlank()); // false
```

Difference:

```java
"   ".isEmpty(); // false
"   ".isBlank(); // true
```

---

# Accessing individual characters

## 6. `charAt(index)`

Returns the character at a particular index.

```java
String text = "Hello";

char first = text.charAt(0);
char last = text.charAt(text.length() - 1);

System.out.println(first); // H
System.out.println(last);  // o
```

An invalid index causes `StringIndexOutOfBoundsException`:

```java
text.charAt(5); // Error because the last valid index is 4
```

## 7. Traversing a string

Forward traversal:

```java
String text = "Hello";

for (int i = 0; i < text.length(); i++) {
    System.out.println(text.charAt(i));
}
```

Backward traversal:

```java
for (int i = text.length() - 1; i >= 0; i--) {
    System.out.print(text.charAt(i));
}
```

Output:

```text
olleH
```

---

# Extracting parts of a string

## 8. `substring(start)`

Returns everything from `start` to the end.

```java
String text = "ABCDEFG";

System.out.println(text.substring(3)); // DEFG
```

Index `3` is included.

## 9. `substring(start, end)`

Returns characters from:

```text
start inclusive to end exclusive
```

```java
String text = "ABCDEFG";

System.out.println(text.substring(2, 5)); // CDE
```

It includes indexes `2`, `3`, and `4`, but not `5`.

A useful way to remember it:

```java
substring(start, end)
```

The resulting length is:

```java
end - start
```

For example:

```java
text.substring(2, 5)
```

has:

```text
5 - 2 = 3 characters
```

## 10. Extracting text between markers

This was used in the XML example:

```java
private static String extract(
        String text,
        String openingTag,
        String closingTag) {

    int start = text.indexOf(openingTag)
              + openingTag.length();

    int end = text.indexOf(closingTag, start);

    return text.substring(start, end);
}
```

Usage:

```java
String xml = "<user><id>101</id></user>";

String id = extract(xml, "<id>", "</id>");

System.out.println(id); // 101
```

Why add `openingTag.length()`?

```text
<id>101</id>
^
indexOf("<id>") points here
```

But we want to start after `<id>`:

```text
<id>101</id>
    ^
    desired starting position
```

---

# Searching inside strings

## 11. `indexOf()`

Returns the first index of a character or substring.

```java
String text = "banana";

System.out.println(text.indexOf('a'));   // 1
System.out.println(text.indexOf("ana")); // 1
```

If it cannot find the value, it returns `-1`:

```java
System.out.println(text.indexOf("xyz")); // -1
```

Therefore, you can check whether something exists:

```java
if (text.indexOf("ana") != -1) {
    System.out.println("Found");
}
```

However, `contains()` is usually clearer for this purpose.

### Start searching from a particular index

```java
String text = "banana";

System.out.println(text.indexOf('a', 2)); // 3
```

It starts searching at index `2`.

## 12. `lastIndexOf()`

Returns the last occurrence.

```java
String text = "banana";

System.out.println(text.lastIndexOf('a'));   // 5
System.out.println(text.lastIndexOf("ana")); // 3
```

A common use is separating a file extension:

```java
String file = "report.final.pdf";

int dot = file.lastIndexOf('.');

String name = file.substring(0, dot);
String extension = file.substring(dot + 1);

System.out.println(name);      // report.final
System.out.println(extension); // pdf
```

## 13. `contains()`

Checks whether a sequence exists.

```java
String text = "Structural Design Pattern";

System.out.println(text.contains("Design")); // true
System.out.println(text.contains("design")); // false
```

It is case-sensitive.

Case-insensitive alternative:

```java
boolean found =
        text.toLowerCase().contains("design".toLowerCase());
```

## 14. `startsWith()` and `endsWith()`

```java
String file = "report.pdf";

System.out.println(file.startsWith("rep")); // true
System.out.println(file.endsWith(".pdf"));  // true
```

Useful for:

- File extensions
    
- Prefixes
    
- Commands
    
- URLs
    
- XML tags
    

```java
if (file.toLowerCase().endsWith(".pdf")) {
    System.out.println("PDF file");
}
```

---

# Comparing strings

## 15. `equals()`

Never normally compare string contents using `==`.

Incorrect:

```java
if (name == "Ishrat") {
    // Unreliable
}
```

Correct:

```java
if (name.equals("Ishrat")) {
    // Compares the characters
}
```

`==` compares whether two references point to the same object.  
`equals()` compares their contents.

Safer when a variable might be `null`:

```java
if ("Ishrat".equals(name)) {
    // Does not crash if name is null
}
```

This can crash if `name == null`:

```java
name.equals("Ishrat");
```

## 16. `equalsIgnoreCase()`

Compares contents without considering uppercase and lowercase.

```java
String answer = "YES";

System.out.println(answer.equalsIgnoreCase("yes")); // true
```

This was useful in the decorator example:

```java
if (transform.equalsIgnoreCase("compress")) {
    response = new CompressionDecorator(response);
}
```

Therefore, all of these are accepted:

```text
compress
COMPRESS
Compress
CoMpReSs
```

## 17. `compareTo()`

Compares strings lexicographically, similar to dictionary ordering.

```java
System.out.println("apple".compareTo("banana")); // Negative
System.out.println("cat".compareTo("cat"));      // 0
System.out.println("dog".compareTo("cat"));      // Positive
```

Use it like this:

```java
int result = first.compareTo(second);

if (result < 0) {
    System.out.println("first comes before second");
} else if (result > 0) {
    System.out.println("first comes after second");
} else {
    System.out.println("They are equal");
}
```

Do not depend on the exact returned number. Usually, only check:

```text
< 0
== 0
> 0
```

Case-insensitive version:

```java
first.compareToIgnoreCase(second);
```

---

# Changing case and removing whitespace

## 18. `toUpperCase()` and `toLowerCase()`

```java
String text = "Hello Java";

System.out.println(text.toUpperCase()); // HELLO JAVA
System.out.println(text.toLowerCase()); // hello java
```

Useful for normalized comparisons:

```java
String command = "START";

if (command.toLowerCase().equals("start")) {
    System.out.println("Starting");
}
```

Though this is simpler:

```java
if (command.equalsIgnoreCase("start")) {
    System.out.println("Starting");
}
```

## 19. `trim()`

Removes ordinary whitespace from the beginning and end.

```java
String text = "   Hello Java   ";

System.out.println(text.trim()); // Hello Java
```

It does not remove spaces inside the string.

```java
"  Hello   Java  ".trim()
```

produces:

```text
Hello   Java
```

## 20. `strip()`, `stripLeading()`, `stripTrailing()`

Modern Unicode-aware alternatives:

```java
String text = "   Hello   ";

System.out.println(text.strip());         // "Hello"
System.out.println(text.stripLeading());  // "Hello   "
System.out.println(text.stripTrailing()); // "   Hello"
```

Generally, prefer `strip()` in modern Java.

---

# Replacing content

## 21. `replace()`

Replaces all literal occurrences.

```java
String text = "banana";

System.out.println(text.replace('a', 'o')); // bonono
```

It also works with strings:

```java
String sentence = "I like Java. Java is useful.";

String result = sentence.replace("Java", "Python");

System.out.println(result);
// I like Python. Python is useful.
```

The original string remains unchanged.

## 22. `replaceFirst()`

Replaces only the first match. It uses a regular expression.

```java
String text = "cat cat cat";

System.out.println(text.replaceFirst("cat", "dog"));
// dog cat cat
```

## 23. `replaceAll()`

Replaces every regular-expression match.

```java
String text = "Room123Server456";

String result = text.replaceAll("\\d", "");

System.out.println(result); // RoomServer
```

Here:

```java
\\d
```

means “a digit” in regular expressions.

Replace multiple spaces with one:

```java
String text = "Hello     Java    World";

String result = text.replaceAll("\\s+", " ");

System.out.println(result); // Hello Java World
```

Important difference:

```java
replace()     // Literal text
replaceAll()  // Regular expression
```

If you do not need regex, prefer `replace()`.

---

# Splitting strings

## 24. `split()`

Splits a string into a `String[]`.

### Split using spaces

```java
String sentence = "Java is very useful";

String[] words = sentence.split(" ");

for (String word : words) {
    System.out.println(word);
}
```

### Split using commas

```java
String data = "Java,Python,C++";

String[] languages = data.split(",");
```

Result:

```text
languages[0] = "Java"
languages[1] = "Python"
languages[2] = "C++"
```

### Comma with optional surrounding spaces

```java
String data = "Java, Python,   C++";

String[] languages = data.split("\\s*,\\s*");
```

This means:

```text
optional whitespace + comma + optional whitespace
```

### Split on one or more whitespace characters

```java
String text = "Java    is\tuseful";

String[] words = text.trim().split("\\s+");
```

`\\s+` means one or more whitespace characters.

### Limit the number of parts

```java
String data = "name:Ishrat:Student";

String[] parts = data.split(":", 2);

System.out.println(parts[0]); // name
System.out.println(parts[1]); // Ishrat:Student
```

Important: `split()` uses regular expressions. Some characters have special meanings.

For example, to split using a literal dot:

```java
String version = "1.2.3";

String[] parts = version.split("\\.");
```

This is incorrect:

```java
version.split(".");
```

In regex, `.` means almost any character.

---

# Joining and concatenating strings

## 25. The `+` operator

```java
String firstName = "John";
String lastName = "Doe";

String fullName = firstName + " " + lastName;

System.out.println(fullName); // John Doe
```

Numbers are converted to strings automatically:

```java
int age = 20;

String message = "Age: " + age;
```

Be careful with calculation order:

```java
System.out.println("Total: " + 10 + 20);   // Total: 1020
System.out.println("Total: " + (10 + 20)); // Total: 30
```

## 26. `concat()`

```java
String result = "Hello".concat(" World");

System.out.println(result); // Hello World
```

Usually, `+` is simpler.

Unlike `+`, `concat()` cannot accept `null` and only accepts a `String`.

## 27. `String.join()`

Joins multiple strings with a separator.

```java
String result = String.join(
        ", ",
        "Java",
        "Python",
        "C++"
);

System.out.println(result);
// Java, Python, C++
```

It also works with a collection:

```java
List<String> names = List.of("Asha", "Ishrat", "Karim");

String result = String.join(" | ", names);

System.out.println(result);
// Asha | Ishrat | Karim
```

## 28. `repeat()`

Repeats a string.

```java
System.out.println("-".repeat(10));
```

Output:

```text
----------
```

Useful for indentation:

```java
String indent = "    ".repeat(3);
System.out.println(indent + "Nested item");
```

---

# Formatting strings

## 29. `String.format()`

Inserts values into placeholders:

```java
String name = "Ishrat";
int age = 20;

String result = String.format(
        "Name: %s, Age: %d",
        name,
        age
);

System.out.println(result);
```

Output:

```text
Name: Ishrat, Age: 20
```

## 30. `.formatted()`

It does the same basic formatting, but is called directly on a string:

```java
String result =
        "Name: %s, Age: %d".formatted("Ishrat", 20);
```

Equivalent:

```java
String result =
        String.format("Name: %s, Age: %d", "Ishrat", 20);
```

Common placeholders:

|Placeholder|Meaning|Example|
|---|---|---|
|`%s`|String or general object|`"Ishrat"`|
|`%d`|Integer|`20`|
|`%f`|Floating-point number|`12.500000`|
|`%.2f`|Floating-point with 2 decimal places|`12.50`|
|`%c`|Character|`'A'`|
|`%b`|Boolean|`true`|
|`%n`|Platform-independent newline|—|
|`%%`|Literal percent sign|`%`|

Example:

```java
String product = "Laptop";
double price = 1250.567;

String output =
        "%s costs $%.2f".formatted(product, price);

System.out.println(output);
// Laptop costs $1250.57
```

## 31. Text blocks

Triple quotes define a multiline string:

```java
String json = """
              {
                  "id": "101",
                  "name": "Ishrat"
              }
              """;
```

Ordinary quotation marks inside a text block generally do not need escaping.

Combined with `.formatted()`:

```java
String id = "101";
String name = "Ishrat";

String json = """
              {
                  "id": "%s",
                  "name": "%s"
              }
              """.formatted(id, name);
```

---

# Conversion methods

## 32. Converting other values to strings

### `String.valueOf()`

```java
int number = 100;
String text = String.valueOf(number);

double price = 15.5;
String priceText = String.valueOf(price);
```

Also works for characters and booleans:

```java
String a = String.valueOf('X');
String b = String.valueOf(true);
```

### `toString()`

Wrapper objects provide `toString()`:

```java
Integer number = 100;
String text = number.toString();
```

For custom objects, you can override `toString()`:

```java
class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{name='%s'}".formatted(name);
    }
}
```

## 33. Converting strings to numbers

```java
int number = Integer.parseInt("123");
double price = Double.parseDouble("19.95");
long population = Long.parseLong("1000000");
boolean active = Boolean.parseBoolean("true");
```

Invalid numeric input causes `NumberFormatException`:

```java
Integer.parseInt("12A"); // Error
```

Safe handling:

```java
try {
    int number = Integer.parseInt(input);
    System.out.println(number);
} catch (NumberFormatException e) {
    System.out.println("Invalid integer");
}
```

## 34. Converting a string to a character array

```java
String text = "Java";

char[] characters = text.toCharArray();

for (char character : characters) {
    System.out.println(character);
}
```

Converting back:

```java
char[] letters = {'H', 'e', 'l', 'l', 'o'};

String text = new String(letters);
```

---

# Pattern matching methods

## 35. `matches()`

Checks whether the entire string matches a regular expression.

```java
String number = "12345";

System.out.println(number.matches("\\d+")); // true
```

Examples:

```java
"abc".matches("[a-z]+");          // true
"ABC123".matches("[A-Z]+\\d+");   // true
"01712345678".matches("01\\d{9}");// true
```

Important: `matches()` checks the entire string.

```java
"abc123xyz".matches("\\d+"); // false
```

Although it contains digits, the whole string is not made of digits.

To check whether it contains a digit:

```java
"abc123xyz".matches(".*\\d+.*"); // true
```

For advanced or repeated regex work, Java also provides:

```java
Pattern
Matcher
```

But these are often unnecessary for basic exam problems.

---

# Lines, indentation and escaping

## 36. Escape sequences

|Syntax|Meaning|
|---|---|
|`\n`|Newline|
|`\t`|Tab|
|`\"`|Double quote|
|`\\`|Backslash|
|`\r`|Carriage return|
|`\b`|Backspace|

Example:

```java
String text =
        "Name:\tIshrat\nCourse:\tCSE-214";

System.out.println(text);
```

Quotes inside a normal string:

```java
String message = "She said, \"Hello\".";
```

Windows-style path:

```java
String path = "C:\\Users\\Student\\file.txt";
```

## 37. `System.lineSeparator()`

Returns the operating system’s newline sequence:

```java
String result = "First line"
        + System.lineSeparator()
        + "Second line";
```

It is similar to `\n`, but platform-independent.

This explains code such as:

```java
status.append(System.lineSeparator())
      .append("  ")
      .append(device.getStatus());
```

It adds:

1. A new line
    
2. Two spaces
    
3. The device’s status
    

## 38. `lines()`

Creates a stream of the individual lines:

```java
String text = """
              Java
              Python
              C++
              """;

text.lines().forEach(System.out::println);
```

For basic programs, `split("\\R")` is another option:

```java
String[] lines = text.split("\\R");
```

---

# `StringBuilder`: efficient string manipulation

## 39. Why use `StringBuilder`?

Because strings are immutable, repeatedly using `+` in a loop can create many temporary objects.

Less efficient for repeated modification:

```java
String result = "";

for (int i = 1; i <= 1000; i++) {
    result = result + i;
}
```

Better:

```java
StringBuilder result = new StringBuilder();

for (int i = 1; i <= 1000; i++) {
    result.append(i);
}

String finalText = result.toString();
```

## 40. Important `StringBuilder` methods

### `append()`

Adds content to the end:

```java
StringBuilder builder = new StringBuilder();

builder.append("Hello");
builder.append(" ");
builder.append("Java");

System.out.println(builder); // Hello Java
```

Method chaining:

```java
builder.append("Name: ")
       .append("Ishrat")
       .append(", Age: ")
       .append(20);
```

### `insert()`

Inserts content at an index:

```java
StringBuilder builder = new StringBuilder("Helo");

builder.insert(3, "l");

System.out.println(builder); // Hello
```

### `delete(start, end)`

Deletes from `start` inclusive to `end` exclusive:

```java
StringBuilder builder =
        new StringBuilder("Hello World");

builder.delete(5, 11);

System.out.println(builder); // Hello
```

### `deleteCharAt()`

```java
StringBuilder builder = new StringBuilder("Helloo");

builder.deleteCharAt(5);

System.out.println(builder); // Hello
```

### `replace(start, end, replacement)`

```java
StringBuilder builder =
        new StringBuilder("Hello Java");

builder.replace(6, 10, "World");

System.out.println(builder); // Hello World
```

### `setCharAt()`

```java
StringBuilder builder = new StringBuilder("Jovo");

builder.setCharAt(1, 'a');
builder.setCharAt(3, 'a');

System.out.println(builder); // Java
```

### `reverse()`

```java
StringBuilder builder = new StringBuilder("Hello");

builder.reverse();

System.out.println(builder); // olleH
```

Easy palindrome check:

```java
String text = "madam";

String reversed =
        new StringBuilder(text).reverse().toString();

boolean palindrome = text.equals(reversed);
```

### `toString()`

Converts the builder into a normal `String`:

```java
StringBuilder builder = new StringBuilder("Hello");

String text = builder.toString();
```

---

# Common practical patterns

## 41. Check for `null`, empty or blank

```java
if (text == null || text.isBlank()) {
    System.out.println("No meaningful text");
}
```

Order matters. Java uses short-circuit evaluation, so if `text == null`, it will not call `text.isBlank()`.

This is unsafe:

```java
if (text.isBlank() || text == null) {
    // May crash before checking null
}
```

## 42. Count occurrences of a character

```java
String text = "banana";
int count = 0;

for (int i = 0; i < text.length(); i++) {
    if (text.charAt(i) == 'a') {
        count++;
    }
}

System.out.println(count); // 3
```

Remember:

```java
'a'   // char
"a"   // String
```

Compare characters using `==`:

```java
text.charAt(i) == 'a'
```

Compare strings using `equals()`:

```java
text.equals("banana")
```

## 43. Reverse a string manually

```java
String text = "Hello";
StringBuilder reversed = new StringBuilder();

for (int i = text.length() - 1; i >= 0; i--) {
    reversed.append(text.charAt(i));
}

System.out.println(reversed); // olleH
```

## 44. Extract username and domain from an email

```java
String email = "student@example.com";

int at = email.indexOf('@');

String username = email.substring(0, at);
String domain = email.substring(at + 1);

System.out.println(username); // student
System.out.println(domain);   // example.com
```

## 45. Capitalize the first character

```java
String word = "java";

String capitalized =
        word.substring(0, 1).toUpperCase()
        + word.substring(1);

System.out.println(capitalized); // Java
```

Safer version:

```java
if (word != null && !word.isEmpty()) {
    word = word.substring(0, 1).toUpperCase()
         + word.substring(1);
}
```

## 46. Remove all spaces

Only ordinary spaces:

```java
String result = text.replace(" ", "");
```

All whitespace:

```java
String result = text.replaceAll("\\s+", "");
```

## 47. Count words

```java
String sentence = "  Java is very useful  ";

int wordCount;

if (sentence == null || sentence.isBlank()) {
    wordCount = 0;
} else {
    wordCount = sentence.trim().split("\\s+").length;
}
```

## 48. Parse a comma-separated line

```java
String data = "Ishrat, 101, 3.85";

String[] parts = data.split("\\s*,\\s*");

String name = parts[0];
int id = Integer.parseInt(parts[1]);
double cgpa = Double.parseDouble(parts[2]);
```

---

# The most important methods to memorize

For a 30–40 minute Java exam, prioritize these:

```java
length()
charAt(index)

substring(start)
substring(start, end)

indexOf(...)
lastIndexOf(...)
contains(...)
startsWith(...)
endsWith(...)

equals(...)
equalsIgnoreCase(...)
compareTo(...)

toUpperCase()
toLowerCase()
trim()
strip()

replace(...)
replaceAll(...)
split(...)

String.join(...)
String.format(...)
formatted(...)

String.valueOf(...)
Integer.parseInt(...)
Double.parseDouble(...)

StringBuilder.append(...)
StringBuilder.insert(...)
StringBuilder.delete(...)
StringBuilder.reverse(...)
StringBuilder.toString()
```

The most important conceptual rules are:

1. String indexes begin at `0`.
    
2. `substring(start, end)` includes `start` but excludes `end`.
    
3. Strings are immutable.
    
4. Use `equals()` rather than `==` for string contents.
    
5. `split()`, `replaceAll()` and `matches()` use regular expressions.
    
6. Use `StringBuilder` for repeated modification, especially inside loops.