Let's cover all four — algorithms, citations, footnotes, and code listings — pulling everything from your fourth document plus the natural surrounding syntax for each.

## PART 1: Algorithms / Pseudocode

### 1. Required Packages

```latex
\usepackage{algorithm}
\usepackage{algpseudocode}
```
Two separate packages working together: `algorithm` provides the **floating container** (numbering, caption, placement — like `figure`/`table`), while `algpseudocode` provides the actual **pseudocode syntax** (`\State`, `\If`, `\While`, etc.) inside it.

### 2. The `algorithm` Environment (outer float)

```latex
\begin{algorithm}[H]
    \caption{Binary Search}
    \label{alg:binary-search}
    \begin{algorithmic}[1]
        ...
    \end{algorithmic}
\end{algorithm}
```
- Behaves exactly like `figure`/`table` — floating placement specifiers (`[h]`, `[t]`, `[H]` with `float` package, etc.) all work identically
- `\caption{}` — numbered caption, e.g. "Algorithm 1: Binary Search"
- `\label{}` — for cross-referencing, same rule as before: must come after `\caption{}`

### 3. The `algorithmic` Environment (inner pseudocode body)

```latex
\begin{algorithmic}[1]
    ...
\end{algorithmic}
```
The `[1]` optional argument turns on **line numbering**, incrementing by 1 for every line. You could use `[2]` to number every second line instead, or omit it entirely for no line numbers.

### 4. Structural Keywords

**Pre/post-conditions:**
```latex
\Require Sorted array $A$, target value $x$
\Ensure Index of $x$, or $-1$ if not found
```
Print "**Require:**" and "**Ensure:**" labels — used to state the algorithm's assumptions and guarantees before the actual steps begin.

**A generic line of pseudocode:**
```latex
\State $low \gets 1$, $high \gets n$
```
`\State` is the basic building block — any single line of pseudocode action. `\gets` renders as the assignment arrow (←).

**Conditional branching:**
```latex
\If{$A[mid] = x$}
    \State \Return $mid$
\ElsIf{$A[mid] < x$}
    \State $low \gets mid + 1$
\Else
    \State $high \gets mid - 1$
\EndIf
```
- `\If{condition}` ... `\EndIf` — every `\If` must be closed with a matching `\EndIf`
- `\ElsIf{condition}` — optional, chainable, for additional branches
- `\Else` — optional, catch-all branch
- The braces after `\If`/`\ElsIf` contain the condition, typeset in math mode automatically

**Loops:**
```latex
\While{$low \leq high$}
    ...
\EndWhile
```
```latex
\For{$i \gets 1$ \textbf{to} $n$}
    ...
\EndFor
```
```latex
\ForAll{$x \in S$}
    ...
\EndFor
```
Each loop type has a matching `\End...` closer — `\While`/`\EndWhile`, `\For`/`\EndFor`, `\ForAll`/`\EndFor` (note `\ForAll` still closes with plain `\EndFor`).

**Returning a value:**
```latex
\State \Return $mid$
```
`\Return` is typically used inside a `\State` line, prints "**return**" in bold/keyword style.

**Function/procedure definitions** (bonus, common addition not in your doc):
```latex
\Function{BinarySearch}{$A, x$}
    ...
\EndFunction
```
```latex
\Procedure{Sort}{$A$}
    ...
\EndProcedure
```

**Comments within pseudocode:**
```latex
\State $low \gets 1$ \Comment{initialize lower bound}
```
`\Comment{}` appends an inline comment, typically right-aligned or in a lighter style.

### 5. Full Example (as in your document)

```latex
\begin{algorithm}[H]
    \caption{Binary Search}
    \label{alg:binary-search}
    \begin{algorithmic}[1]
        \Require Sorted array $A$, target value $x$
        \Ensure Index of $x$, or $-1$ if not found
        \State $low \gets 1$, $high \gets n$
        \While{$low \leq high$}
            \State $mid \gets \lfloor (low + high)/2 \rfloor$
            \If{$A[mid] = x$}
                \State \Return $mid$
            \ElsIf{$A[mid] < x$}
                \State $low \gets mid + 1$
            \Else
                \State $high \gets mid - 1$
            \EndIf
        \EndWhile
        \State \Return $-1$
    \end{algorithmic}
\end{algorithm}
```

### 6. Cross-Referencing

```latex
Algorithm~\ref{alg:binary-search} demonstrates the search procedure.
```
Works exactly like figures/tables/equations — `\ref{}` pulls the auto-number, `~` prevents an awkward line break between "Algorithm" and the number.

---

## PART 2: Citations — `natbib`

### 1. Loading the Package

```latex
\usepackage[numbers]{natbib}
```
The `[numbers]` option makes citations render as numeric labels, e.g. `[1]`, `[2]`. Alternative:
```latex
\usepackage[authoryear,round]{natbib}
```
This instead renders citations as "(Author, Year)" style, with `round` specifying round parentheses (`square` is the alternative, for `[Author, Year]`).

### 2. The Underlying Workflow

Citations in LaTeX aren't just a command — they depend on an external **`.bib` file** (a small database of sources) plus a **compile step using BibTeX** (or the newer BibLaTeX/Biber, though your document uses classic BibTeX).

**The `.bib` file** (e.g., `references.bib`) contains entries like:
```latex
@inproceedings{vaswani2017attention,
  author = {Ashish Vaswani and others},
  title = {Attention Is All You Need},
  booktitle = {Advances in Neural Information Processing Systems},
  year = {2017}
}
```
- `@inproceedings` — the **entry type** (others: `@article`, `@book`, `@phdthesis`, `@misc`, etc.), each expecting slightly different required fields
- `vaswani2017attention` — the **citation key**, the unique identifier you'll reference in `\cite`-family commands
- The fields inside (`author`, `title`, `booktitle`/`journal`, `year`, etc.) supply the actual bibliographic details, formatted according to whatever `\bibliographystyle{}` you choose

### 3. Plain BibTeX (baseline, no natbib)

```latex
\cite{vaswani2017attention}
\cite{krizhevsky2012imagenet,he2016deep}
```
This built-in command works with or without `natbib` loaded — multiple keys can be cited together in one call, comma-separated. With `[numbers]` style, this typically renders as `[1]` or `[1, 2]`.

### 4. Natbib's Expanded Commands

**`\citet{}` — "textual" citation, author name flows into the sentence:**
```latex
\citet{vaswani2017attention} introduced the Transformer architecture.
```
Renders roughly as: "Vaswani et al. (2017) introduced the Transformer architecture." (in author-year mode) or "Vaswani et al. [1] introduced..." (in numeric mode).

**`\citep{}` — "parenthetical" citation, evidence tacked onto the end of a claim:**
```latex
Residual learning became a standard CNN technique \citep{he2016deep}.
```
Renders as: "Residual learning became a standard CNN technique (He et al., 2016)." or "...technique [2]."

**Multiple sources in one `\citep`:**
```latex
\citep{krizhevsky2012imagenet,he2016deep}
```

**Author-only or year-only:**
```latex
\citeauthor{he2016deep} proposed residual learning in \citeyear{he2016deep}.
```
`\citeauthor{}` prints just the author name(s); `\citeyear{}` prints just the year — useful when you want to construct a custom sentence structure around the citation manually.

### 5. `\nocite{}` — Including Sources Without an In-Text Citation

```latex
\nocite{lamport1994latex}
```
This prints **nothing** at its location in the text — it just tells BibTeX "include this entry in the final bibliography anyway," even though it was never actually cited anywhere in the body.

```latex
\nocite{*}
```
Special case: includes **every single entry** from the `.bib` file in the bibliography, cited or not. Useful for a demo/reference list, but generally too broad for an actual finished paper.

### 6. Printing the Bibliography

```latex
\bibliographystyle{plainnat}
\bibliography{references}
```
- `\bibliographystyle{}` — selects the **formatting rules** for how each reference entry is printed (order, punctuation, italics, etc.)
- `\bibliography{}` — points to the `.bib` file by name, **without** the `.bib` extension

**Common style options:**
| Style | Behavior |
|---|---|
| `plainnat` | natbib-friendly, works with both numeric and author-year modes |
| `plain` | Numeric labels, sorted alphabetically by author |
| `abbrv` | Like `plain`, but abbreviates first names |
| `unsrt` | Numeric labels, ordered by first citation appearance (not alphabetical) |
| `ieeetr` | IEEE-style numeric, common in engineering papers |
| `apalike` | Author-year style, resembling APA format |

### 7. The Multi-Compile Bibliography Process

Getting citations and the reference list to appear correctly requires a specific compile sequence (this is a common source of confusion):
1. Run **LaTeX** (or pdfLaTeX) — this generates an `.aux` file listing which keys were cited
2. Run **BibTeX** — reads the `.aux` file and the `.bib` file, produces a `.bbl` file with formatted references
3. Run **LaTeX** again — pulls in the `.bbl` content to actually place the bibliography
4. Run **LaTeX** a third time (sometimes needed) — resolves any remaining cross-reference numbers

Most editors (Overleaf included) automate this whole chain when you hit "compile" — but it's worth knowing why a fresh document sometimes shows `[?]` or missing references until it's been compiled a couple of times.

---

## PART 3: Footnotes

### 1. Basic Usage — No Package Needed

```latex
Use \verb|\footnote{...}| for short side comments.\footnote{This is a regular
footnote. It appears at the bottom of the page.}
```
`\footnote{}` is entirely built into LaTeX — no `\usepackage` required. It:
- Automatically numbers itself, incrementing across the whole document
- Places the note text at the **bottom of the current page** (not the end of the document)
- Renders a small superscript number at the point in the text where `\footnote{}` was called, matching the number at the bottom

### 2. Placement Rule

The footnote command goes **immediately after the word/punctuation** it's attached to, with no space:
```latex
This is an important claim.\footnote{Source: some study.}
```
Not:
```latex
This is an important claim. \footnote{Source: some study.}
```
(A stray space before `\footnote` can introduce unwanted spacing before the superscript number.)

### 3. Reusing the Same Footnote Number

LaTeX doesn't have a single command for "cite the same footnote twice" — instead, the standard trick is to **label** the first footnote, then reference that label's number using `\footnotemark`:

```latex
First mention.\footnote{\label{fn:tool}A repeated note.}
Second mention.\footnotemark[\ref{fn:tool}]
```
- The first `\footnote{}` includes a `\label{}` inside it, capturing that specific footnote's number
- `\footnotemark[\ref{fn:tool}]` at the second location prints just the **superscript number** again (matching the first one), without creating a second full footnote text at the bottom of the page

This is a workaround, not a dedicated single command — worth knowing it's assembled from two more basic pieces (`\label`/`\ref` + `\footnotemark`).

### 4. `\footnotemark` Alone (without a matching number reference)

```latex
This needs a note.\footnotemark
```
Used alone, `\footnotemark` just places a superscript number without any accompanying text — typically paired later with:
```latex
\footnotetext{The actual note text goes here.}
```
This split (`\footnotemark` + separate `\footnotetext{}`) is useful in tricky contexts like inside a `tabular` or `minipage`, where a plain `\footnote{}` might not place correctly.

### 5. Practical Guidance (from your document)

> Avoid long footnotes in short reports. If the note becomes a full paragraph, it probably belongs in the main text.

This is stylistic advice, not a syntax rule — footnotes are best kept to a sentence or two; longer tangents usually deserve to be integrated into the body or moved to an appendix.

---

## PART 4: Code Listings — `listings` (recap + full detail)

Since this came up in an earlier question too, here's the consolidated version alongside the other three topics for completeness.

### 1. Loading and Global Config

```latex
\usepackage{listings}
```
```latex
\lstset{
    basicstyle=\ttfamily\small,
    keywordstyle=\color{customblue}\bfseries,
    commentstyle=\color{teal!70!black},
    stringstyle=\color{red!70!black},
    frame=single,
    breaklines=true,
    showstringspaces=false,
    columns=fullflexible
}
```
`\lstset{}` sets **defaults for every `lstlisting` block** in the document, so individual blocks don't need to repeat styling options.

| Option | Effect |
|---|---|
| `basicstyle` | Base font (e.g., monospace, size) |
| `keywordstyle` | Styling for language keywords (`if`, `def`, `return`, etc.) |
| `commentstyle` | Styling for comments |
| `stringstyle` | Styling for string literals |
| `frame=single` | Draws a border box around the code |
| `breaklines=true` | Wraps long lines instead of overflowing the page |
| `showstringspaces=false` | Hides visible space markers inside strings |
| `columns=fullflexible` | More natural, proportional character spacing |
| `captionpos=b`            |        Caption below the code|
### Listing from a file
```latex
\lstinputlisting[language=Python,
    caption={Computing samples of the RC step response.},
    label={lst:python-code},
    captionpos=b                   % Caption below the code
]{script.py}
```

### 2. The `lstlisting` Environment

```latex
\begin{lstlisting}[language=Python, caption={A small Python function.}, label={lst:python-square}]
def greet_user(name):
    # Create a greeting message
    message = f"Hello, {name}!"
    return message
\end{lstlisting}
```
- `language=` — enables syntax highlighting rules specific to that language (`Python`, `C`, `Java`, `SQL`, etc.)
- `caption={}` — numbered caption, like `figure`/`table`/`algorithm`
- `label={}` — cross-referencing tag

### 3. Cross-Referencing

```latex
Listing~\ref{lst:python-square} shows the function.
```
Works identically to figures/tables/algorithms — `lstlisting` behaves as a numbered floating-style element when captioned.

### 4. Inline Code

```latex
Use \lstinline{print()} to display output.
```
The inline equivalent of `\verb`, but with `listings`-style syntax highlighting applied.

### 5. `\listoflistings`

```latex
\listoflistings
```
Parallel to `\listoffigures`/`\listoftables` — auto-generates a page listing every captioned code block.

---

## Quick Reference — All Four Topics

| Topic | Key Syntax |
|---|---|
| **Algorithm** | `\begin{algorithm}[H]\caption{}\label{}\begin{algorithmic}[1]\State \gets \If\ElsIf\Else\EndIf \While\EndWhile \Return\end{algorithmic}\end{algorithm}` |
| **Citation (natbib)** | `\citet{key}`, `\citep{key}`, `\citeauthor{key}`, `\citeyear{key}`, `\nocite{key}`, `\bibliographystyle{}`, `\bibliography{file}` |
| **Footnote** | `\footnote{text}`, reuse via `\label{}` inside + `\footnotemark[\ref{}]`, or split via `\footnotemark` + `\footnotetext{}` |
| **Code Listing** | `\lstset{}` (global defaults), `\begin{lstlisting}[language=,caption=,label=]`, `\lstinline{}`, `\listoflistings` |

Let me know what's next.

Let's cover hyperref, font sizing, and color fully — pulling everything that appeared across your four documents plus the natural surrounding syntax.

## PART 1: `hyperref`

### 1. Loading the Package

```latex
\usepackage{hyperref}
```
**Important placement rule:** `hyperref` should almost always be the **last package loaded** in your preamble (or very close to last). It modifies the behavior of many other packages/commands to make them clickable, so loading it early can cause conflicts or lost functionality.

### 2. What It Does Automatically

Once loaded, `hyperref` doesn't just add new commands — it **quietly makes existing things clickable** that weren't before:
- Every `\ref{}` becomes a clickable jump-to-target link
- Every `\cite{}`/`\citep{}`/`\citet{}` becomes a clickable link to the bibliography entry
- Every table-of-contents entry becomes clickable, jumping to that section
- The document gets embedded PDF metadata (title, sometimes author)

### 3. The `[hidelinks]` Option

```latex
\usepackage[hidelinks]{hyperref}
```
By default, `hyperref` draws colored boxes/borders around every clickable link — which many people find visually intrusive in a plain academic document. `[hidelinks]` disables those visible boxes entirely, so links remain clickable but look like normal text (no color, no border).

### 4. `\hypersetup{}` — Custom Link Styling

```latex
\hypersetup{
    colorlinks=true,
    linkcolor=customblue,
    citecolor=customblue,
    urlcolor=teal
}
```
This is an **alternative to `[hidelinks]`** — instead of hiding the link styling entirely, this makes links appear as **colored text** (no boxes), with different colors depending on what kind of link it is:

| Option | Controls the color of... |
|---|---|
| `linkcolor` | Internal links — `\ref{}` (figures, tables, sections, equations) |
| `citecolor` | Citation links — `\cite{}`, `\citep{}`, `\citet{}` |
| `urlcolor` | `\url{}` and `\href{}` links |
| `colorlinks=true` | Master switch — must be `true` for the color options above to take effect at all; if `false` (or omitted), you're back to boxed borders (or nothing, if `[hidelinks]` is also set) |

**Note:** you'd typically use *either* `[hidelinks]` *or* `\hypersetup{colorlinks=true,...}` — not both, since they're two different visual approaches to the same underlying problem (making links visible without ugly boxes).

### 5. `\href{}` — Custom Display Text Linking to a URL

```latex
\href{https://www.overleaf.com/learn/latex/Main_Page}{Overleaf Learn LaTeX}
```
Two arguments: `{URL}{display text}`. The reader sees "Overleaf Learn LaTeX" as clickable text, but it links to the given URL. Use this whenever you want the link text to be different/more readable than the raw URL itself.

### 6. `\url{}` — Print the URL Literally

```latex
\url{https://www.overleaf.com}
```
Displays the URL itself as the clickable text (typically in monospace font). Use this when you specifically want the reader to see the actual web address, not custom text.

### 7. Internal Cross-References Become Clickable (no new syntax needed)

Since `hyperref` was already covered in the figures/tables/equations lessons, it's worth reiterating: commands you already know — `\ref{}`, `\label{}`, `\cite{}` family — don't change syntactically at all. `hyperref` just makes their *rendered output* clickable once the package is loaded. No extra commands required on your part for that basic behavior.

### 8. `\autoref{}` (bonus, not in your docs but commonly paired with hyperref)

```latex
\autoref{fig:my-figure}
```
Unlike plain `\ref{}` (which only gives the number), `\autoref{}` automatically prepends the type name too — e.g., renders as "**figure** 3" or "**section** 2" — inferred from what kind of `\label{}` it points to. Saves you from manually typing "Figure~\ref{...}" every time.

---

## PART 2: Font Size Commands

### 1. The Full Size Ladder

LaTeX defines ten relative font-size commands, smallest to largest:

```latex
\tiny \scriptsize \footnotesize \small \normalsize \large \Large \LARGE \huge \Huge
```

Across your documents, most of these appeared: `\tiny`, `\small`, `\large`, `\Large`, `\LARGE`, `\huge`, `\Huge` (skipping `\scriptsize`, `\footnotesize`, `\normalsize` — but they exist and work exactly the same way).

### 2. Two Syntax Styles: Declaration vs. Argument-Taking

This is a subtlety worth being precise about, since your document (image example) showed both forms mixed together.

**Declaration form** (the "correct"/standard way) — no braces of its own, it just changes the size of everything until the enclosing scope ends:
```latex
{\Large This text is large.}
{\small This text is small.}
```
The **outer** `{ }` braces are what *you* add to limit the scope — `\Large` itself doesn't take an argument. Everything between the opening `{` and closing `}` is affected.

**"Argument-taking" looking form** (works, but is slightly non-idiomatic):
```latex
\Large{Large Text}
\small{small text}
```
This *also* works and looks similar, but technically what's happening is different: `\Large{Large Text}` is really `\Large` (which affects everything after it) followed by a *separate* group `{Large Text}` that happens to immediately follow it. Since the braces close right after "Large Text", the size change is scoped to just that word — but only *because* the braces are there, not because `\Large` "takes" that text as a formal argument the way `\textbf{}` does.

**The actual difference in practice:**
```latex
\Large{Large Text}. This continues normal size.
```
✅ Works correctly — the `.` and following text return to normal size because they're outside the `{ }`.

```latex
\Large Large Text. This is also large!
```
⚠️ Also valid syntax, but now **everything** after `\Large` stays large indefinitely, until some other size command or the end of the current group/environment resets it — because there's no closing brace to limit the scope.

**Bottom line:** both forms you saw in the image work fine as long as braces are present somewhere to close the scope — but the cleaner, more conventional style is:
```latex
{\Large Large Text}
```
with the braces wrapping the whole declaration+text together, since it makes the scope visually obvious.

### 3. Approximate Point Sizes (relative to 10pt base document)

| Command | Approx. size (10pt base) |
|---|---|
| `\tiny` | 5pt |
| `\scriptsize` | 7pt |
| `\footnotesize` | 8pt |
| `\small` | 9pt |
| `\normalsize` | 10pt (the document default) |
| `\large` | 12pt |
| `\Large` | 14.4pt |
| `\LARGE` | 17.28pt |
| `\huge` | 20.74pt |
| `\Huge` | 24.88pt |

These are all **relative** to whatever base size you set in `\documentclass[Npt]{article}` — if you compile with `12pt` instead of `10pt`, every one of these scales up proportionally too.

### 4. Applying Size to a Whole Paragraph/Section

```latex
\begin{center}
\small
\begin{tabular}{...}
...
\end{tabular}
\end{center}
```
This is exactly how your third document sized an entire table smaller — `\small` here isn't wrapped in its own `{ }`, so it applies to everything until the enclosing `center` environment ends (environments create their own implicit scope boundary, so you don't strictly need extra braces here).

---

## PART 3: Color — `xcolor` Package

### 1. Loading the Package

```latex
\usepackage{xcolor}
```
Or, for table-specific coloring capability (as seen in your fourth document):
```latex
\usepackage[table]{xcolor}
```
The `[table]` option is required specifically to unlock `\rowcolors` and `\cellcolor` working properly inside `tabular` — plain `xcolor` alone gives you `\textcolor`/`\colorbox` but not the table-row coloring machinery.

### 2. Built-In Named Colors

`xcolor` ships with a base set of names usable immediately:
```latex
red, green, blue, yellow, orange, purple, cyan, magenta, black, white, gray, brown, pink, teal, violet, lime, olive
```

### 3. `\textcolor{}` — Colored Text

```latex
\textcolor{red}{red text}
\textcolor{customblue}{a custom phrase}
```
Two arguments: `{color name}{text to color}`. Only the text color changes — background stays transparent/page-colored.

### 4. `\colorbox{}` — Colored Background Highlight

```latex
\colorbox{softyellow}{short highlighted note}
```
Fills a background box behind the text — like a highlighter marker effect. Text color itself stays default (black) unless combined with `\textcolor` too.

**Combining both** for colored background + colored text:
```latex
\colorbox{black}{\textcolor{white}{white text on black background}}
```

### 5. `\fcolorbox{}` — Colored Box with a Border (bonus, not in your docs)

```latex
\fcolorbox{black}{softyellow}{Bordered highlighted text}
```
Three arguments: `{border color}{background color}{text}`.

### 6. `\definecolor{}` — Creating Custom Named Colors

```latex
\definecolor{customblue}{RGB}{0, 76, 153}
\definecolor{softgreen}{RGB}{226, 245, 236}
\definecolor{softred}{RGB}{255, 232, 232}
\definecolor{softyellow}{RGB}{255, 247, 214}
```
Goes in the preamble. Syntax: `\definecolor{your-chosen-name}{color-model}{values}`.

**Color models available:**
```latex
\definecolor{name}{RGB}{0,76,153}       % Red/Green/Blue, 0–255 each
\definecolor{name}{rgb}{0.0,0.3,0.6}    % rgb, 0.0–1.0 each (lowercase!)
\definecolor{name}{HTML}{004C99}        % hex code, no # symbol
\definecolor{name}{gray}{0.5}           % single value 0 (black) to 1 (white)
\definecolor{name}{cmyk}{0.9,0.5,0,0.4} % Cyan/Magenta/Yellow/Key(black), 0.0–1.0 each
```
Once defined, the name is usable anywhere in the document exactly like a built-in color: `\textcolor{customblue}{...}`.

### 7. Color Mixing Syntax — `!`

```latex
gray!10          % 10% gray, 90% white
teal!70!black    % 70% teal, 30% black
red!50!blue      % 50% red, 50% blue (a purple)
```
Pattern: `color!percentage` blends toward **white** by default. `color1!percentage!color2` blends between two specific named colors by that percentage. This is extremely common for subtle shading (like light gray table stripes) without needing to `\definecolor` a whole new name just for a tint.

### 8. Using Color in Tables (recap, tying back to the tables lesson)

```latex
\rowcolors{2}{gray!10}{white}    % needs xcolor[table]
\cellcolor{softgreen}Correct
```

### 9. Full Worked Example (all three topics combined)

```latex
\usepackage[table]{xcolor}
\usepackage{hyperref}

\definecolor{customblue}{RGB}{0, 76, 153}

\hypersetup{
    colorlinks=true,
    linkcolor=customblue,
    citecolor=customblue,
    urlcolor=teal
}

\begin{document}

{\Large \textcolor{customblue}{Section Highlight}}

This paragraph mentions \textcolor{red}{an important warning} and provides a
\colorbox{yellow}{highlighted key term}.

See \href{https://www.overleaf.com}{Overleaf} for more, or visit
\url{https://www.overleaf.com} directly.

{\small This is a smaller closing note in the default color.}

\end{document}
```

## Quick Reference Table

| Syntax | Purpose |
|---|---|
| `\usepackage[hidelinks]{hyperref}` | Load hyperref, hide link boxes |
| `\hypersetup{colorlinks=true, linkcolor=..., citecolor=..., urlcolor=...}` | Custom colored link styling |
| `\href{url}{text}` | Clickable link with custom display text |
| `\url{url}` | Clickable link showing the raw URL |
| `\autoref{label}` | Auto-labeled reference (e.g. "figure 3") |
| `{\Large ... }` | Declaration-style font size, scoped by braces |
| `\Large{...}` | Also works, but braces are a separate group, not a true argument |
| `\tiny` → `\Huge` | Full relative size ladder (10 steps) |
| `\usepackage[table]{xcolor}` | Load xcolor with table-coloring support |
| `\definecolor{name}{model}{values}` | Create a custom reusable color |
| `\textcolor{color}{text}` | Colored text |
| `\colorbox{color}{text}` | Colored background box |
| `\fcolorbox{border}{bg}{text}` | Bordered colored box |
| `color!N` | Blend N% color with white |
| `color1!N!color2` | Blend N% color1 with color2 |

Let me know what's next.
This is one of the richest topics across your documents — let's cover it completely, from the basic environment through wrapped figures and subfigures.

## 1. Required Package

```latex
\usepackage{graphicx}
```
This is what actually gives you `\includegraphics` — without it, you can't insert images at all. The `figure` *environment* itself is built into LaTeX (no package needed), but it's useless without something to put inside it.

## 2. The Basic `figure` Environment

```latex
\begin{figure}[h]
    \centering
    \includegraphics[width=0.5\linewidth]{image.png}
    \caption{A description of the image.}
    \label{fig:my-label}
\end{figure}
```

This is a **floating environment** — meaning LaTeX doesn't guarantee it appears exactly where you typed it in the source. It "floats" to wherever LaTeX judges is the best spot on the page, based on the placement specifier you give it and how much space is available.

## 3. Placement Specifiers — `[h]`, `[t]`, `[b]`, `[p]`, `[!]`

```latex
\begin{figure}[h]   % here — try to place near this point in the text
\begin{figure}[t]   % top — allow placement at the top of a page
\begin{figure}[b]   % bottom — allow placement at the bottom of a page
\begin{figure}[p]   % page — a separate page containing only floats
\begin{figure}[!]   % relax LaTeX's internal placement restrictions (modifier, used with others)
```

**Key point: these are hints, not commands.** `[h]` does *not* guarantee "exactly here" — it just tells LaTeX's placement algorithm to prefer that location if reasonably possible. LaTeX may still move the figure if, say, there isn't enough room left on the current page.

You can combine multiple letters to give LaTeX more flexibility, in priority order:
```latex
\begin{figure}[htbp]    % try here, then top, then bottom, then a dedicated page
\begin{figure}[!htbp]   % same, but with relaxed internal restrictions
```

## 4. Forcing Exact Placement — `[H]`

```latex
\usepackage{float}
...
\begin{figure}[H]
```
Capital `H` (from the `float` package — **not** built into LaTeX by default) forces the figure to render **exactly** at that point in the source, no floating at all. This is the only placement option that's a true command rather than a suggestion.

- Convenient for drafts, demos, and tutorials where predictable placement matters more than typographic polish.
- Discouraged in polished/final documents, because it can force awkward gaps or push content onto a near-empty page if there isn't enough room — LaTeX's normal floating algorithm usually produces better-looking results when left alone.

## 5. `\centering`

```latex
\centering
```
Horizontally centers whatever comes after it (typically the image) within the current text width. Goes *inside* the `figure` environment, right before `\includegraphics`.

## 6. `\includegraphics` — Full Options

```latex
\includegraphics[width=0.5\linewidth]{image.png}
```

**Sizing options:**
```latex
\includegraphics[width=0.5\linewidth]{img.png}    % 50% of the current text width — most predictable/portable
\includegraphics[width=5cm]{img.png}                % fixed absolute width
\includegraphics[height=4cm]{img.png}               % fixed absolute height
\includegraphics[scale=0.5]{img.png}                % resize by a multiplicative factor of the image's native size
```

**Rotation:**
```latex
\includegraphics[angle=8]{img.png}       % rotate 8 degrees counter-clockwise
\includegraphics[angle=-90]{img.png}     % rotate clockwise (negative angle)
```

**Combining options** (comma-separated):
```latex
\includegraphics[width=0.28\linewidth, angle=8]{image.png}
```

**Best practice noted in your document:** prefer `width=...\linewidth` over `scale=` or fixed units, because it automatically adapts if your page margins/column width ever change — a fixed `cm` value won't.

## 7. `\caption{}`

```latex
\caption{A DFA that accepts binary strings containing an even number of 1s.}
```
- Adds a numbered caption below (by convention) the figure: "Figure 1: ..."
- The number auto-increments per figure across the whole document
- Powers `\listoffigures` (see below) and is what text `\ref{}` displays alongside the number when cross-referenced

## 8. `\label{}` and `\ref{}` — Cross-Referencing

```latex
\label{fig:even-ones-dfa}
...
Figure~\ref{fig:even-ones-dfa} shows the automaton.
```

**Critical rule, emphasized in your documents: `\label{}` must come *after* `\caption{}`**, not before. This is because `\label` stores whatever the *current* figure number is at the point it's called — and that number isn't finalized until `\caption` has run. Put `\label` before `\caption` and it'll grab the *previous* figure's number (or nothing), causing a subtly wrong cross-reference — one of the most common LaTeX mistakes.

```latex
% CORRECT
\caption{My caption}
\label{fig:example}

% WRONG — label often points to wrong number
\label{fig:example}
\caption{My caption}
```

The `~` between "Figure" and `\ref{}` is a **non-breaking space**, preventing the figure name and number from ever splitting across a line break.

Also requires **two compiles**, same as `\tableofcontents` — the first compile writes the label's number to the `.aux` file, the second compile can display it correctly.

## 9. Multiple Images Side-by-Side (without formal subfigures)

```latex
\begin{figure}[H]
    \centering
    \includegraphics[width=0.28\linewidth]{img1.jpg}
    \hfill
    \includegraphics[scale=0.16]{img2.jpg}
    \hfill
    \includegraphics[width=0.28\linewidth, angle=8]{img3.png}
    \caption{One caption covering all three images.}
    \label{fig:size-scale-angle}
\end{figure}
```
`\hfill` inserts flexible horizontal space that expands to push images apart evenly. This gives you multiple images in one figure block, but with only **one shared caption/label** for the whole group — for individually captioned images, you need subfigures instead (next section).

## 10. Subfigures — `subcaption` Package

```latex
\usepackage{subcaption}
```

```latex
\begin{figure}[H]
    \centering
    \begin{subfigure}{0.45\linewidth}
        \centering
        \includegraphics[width=\linewidth]{img1.jpg}
        \caption{Country scale}
        \label{fig:sub-country}
    \end{subfigure}
    \hfill
    \begin{subfigure}{0.45\linewidth}
        \centering
        \includegraphics[width=\linewidth]{img2.jpg}
        \caption{River scale}
        \label{fig:sub-river}
    \end{subfigure}
    \caption{Two subfigures side by side.}
    \label{fig:overall}
\end{figure}
```

- Each `subfigure` is its own mini-figure with its **own width, own caption, own label** — automatically numbered like "1a", "1b" (sub-labels of the outer figure's number)
- The outer `\caption`/`\label` covers the whole group
- You can reference either the whole thing (`\ref{fig:overall}` → "1") or just one part (`\ref{fig:sub-river}` → "1b")
- `\hfill` again separates them horizontally
- give a blank line to start a new row `\vspace` is for widening the gap
- To start a **second row** of subfigures, insert `\vspace{...}` after a `\hfill`-separated row, then continue with more `subfigure` blocks

```latex
\vspace{0.35cm}    % gap before the next row
\begin{subfigure}{0.45\linewidth}
    ...
\end{subfigure}
```

**Critical warning from your document:** never put a **blank line** between subfigures in your source code. A blank line starts a new paragraph, which forces the next subfigure onto a new line regardless of whether it would actually fit — breaking your intended side-by-side layout. Keep the `\end{subfigure}`, `\hfill`, `\begin{subfigure}` sequence with no blank lines in between.

## 11. Wrapped Figures — `wrapfig` Package

```latex
\usepackage{wrapfig}
```

```latex
\begin{wrapfigure}{r}{0.36\linewidth}
    \centering
    \includegraphics[width=\linewidth]{image.jpg}
    \caption{Wrapped image.}
    \label{fig:wrapped}
\end{wrapfigure}

Body text goes here and will automatically flow around the image instead of
stopping above or below it...
```

- First `{ }` argument: side placement — `l` (left), `r` (right), `c` (center, rarely useful since text can't wrap both sides), or starred variants (`L`, `R`) that allow more aggressive/exact placement
- Second `{ }` argument: width of the wrapped figure, usually as a fraction of `\linewidth`
- Text in the following paragraph(s) automatically flows around it — this is the key difference from a normal `figure`, which always sits alone on its own vertical block
- **Practical tips from your document:** keep the width modest (roughly `0.25\linewidth` to `0.40\linewidth`) and make sure there's *enough following text* to fill the space beside the image — too little text after it can cause the next paragraph to butt up too closely against the float.

## 12. `\listoffigures`

```latex
\listoffigures
```
Parallel to `\tableofcontents` and `\listoftables` — auto-generates a page listing every figure's caption text, number, and page, pulled from every `\caption{}` inside a `figure` (or `subfigure`) environment. Also needs the standard two-compile cycle to populate correctly.

## 13. Two-Column Documents — `figure*`

```latex
\documentclass[twocolumn]{article}
...
\begin{figure*}[t]
    \centering
    \includegraphics[width=0.8\textwidth]{wide-image.png}
    \caption{A figure spanning both columns.}
    \label{fig:wide}
\end{figure*}
```
In a two-column layout, a normal `figure` is confined to a single column's width. The **starred** `figure*` spans across **both columns**, useful for wide images/diagrams that wouldn't fit in a single column.

## 14. Full Worked Example (combining everything)

```latex
\usepackage{graphicx}
\usepackage{float}
\usepackage{wrapfig}
\usepackage{subcaption}

% Standard figure, forced placement
\begin{figure}[H]
    \centering
    \includegraphics[width=0.6\linewidth]{diagram.png}
    \caption{System architecture overview.}
    \label{fig:architecture}
\end{figure}

As shown in Figure~\ref{fig:architecture}, the system consists of...

% Wrapped figure
\begin{wrapfigure}{r}{0.3\linewidth}
    \centering
    \includegraphics[width=\linewidth]{icon.png}
    \caption{Logo.}
    \label{fig:logo}
\end{wrapfigure}

This paragraph flows around the wrapped image on the right...

\clearpage

% Subfigures
\begin{figure}[H]
    \centering
    \begin{subfigure}{0.45\linewidth}
        \centering
        \includegraphics[width=\linewidth]{before.png}
        \caption{Before}
        \label{fig:before}
    \end{subfigure}
    \hfill
    \begin{subfigure}{0.45\linewidth}
        \centering
        \includegraphics[width=\linewidth]{after.png}
        \caption{After}
        \label{fig:after}
    \end{subfigure}
    \caption{Comparison before and after processing.}
    \label{fig:comparison}
\end{figure}
```

## Quick Reference Table

| Syntax | Purpose |
|---|---|
| `\begin{figure}[h/t/b/p]` | Standard floating figure, placement hint |
| `[H]` (needs `float`) | Force exact placement, no floating |
| `\centering` | Horizontally center contents |
| `\includegraphics[width=/scale=/angle=]{file}` | Insert and size/rotate an image |
| `\caption{}` | Numbered caption |
| `\label{}` (after caption!) | Tag for cross-referencing |
| `\ref{}` | Pull the auto-number of a labeled figure |
| `\hfill` | Flexible space to separate multiple images/subfigures |
| `\begin{subfigure}{width}` (needs `subcaption`) | Individually captioned sub-image within a figure |
| `\begin{wrapfigure}{l/r}{width}` (needs `wrapfig`) | Figure with text flowing around it |
| `\listoffigures` | Auto-generated list of all figure captions |
| `figure*` (two-column docs) | Figure spanning both columns |
| `\clearpage` | Flush pending floats before continuing |

Let me know what's next.

Let's cover text formatting commands and the sectioning hierarchy in this response — I'll do line breaks and `multicol` next.

## PART 1: Text Formatting Commands

### 1. `\textbf{}` — Bold Text

```latex
\textbf{Bold text}
```
Makes the enclosed text bold. Takes its argument in braces like a normal command (unlike the font-size *declarations* from earlier, this always requires `{ }` around exactly the text you want affected).

```latex
This is \textbf{important} but this is not.
```

### 2. `\textit{}` — Italic Text

```latex
\textit{This is italicized.}
```
Forces the text into italics, **regardless of context** — even if you're already inside another italic block, `\textit{}` will just keep it italic (it doesn't "toggle" or adapt).

### 3. `\emph{}` — Emphasis (context-aware)

```latex
\emph{This will be emphasized.}
```
This looks nearly identical to `\textit{}` in normal (upright) text — both render italic. But the key difference is that `\emph{}` is **contextual**: if you nest it inside text that's *already* italic (e.g., inside another `\emph{}`, or inside a naturally italicized environment like a `quote` with italic styling), it flips to upright/roman instead, to keep the emphasis visually distinct from its surroundings.

```latex
\emph{This is emphasized, and \emph{this nested part} flips back to upright.}
```
Whereas:
```latex
\textit{This is italic, and \textit{this nested part} stays italic too.}
```

**Practical rule of thumb:** use `\emph{}` for genuine linguistic/semantic emphasis (like emphasizing a word in a sentence), and `\textit{}` when you specifically want italic styling regardless of surrounding context (like a book title, or a foreign-language phrase).

### 4. `\underline{}` — Underlined Text

```latex
\underline{underlined text}
```
Draws a line directly under the text. Note: this is a fairly "blunt" built-in tool — it doesn't break nicely across multiple lines if the underlined text is long enough to wrap, and typographically, underlining is often discouraged in professional documents in favor of italics or bold for emphasis (a stylistic convention, not a LaTeX limitation). If you need better-behaved underlining, the `soul` package's `\ul{}` command handles line-wrapping better, but that's outside what appeared in your files.

### 5. `\texttt{}` — Typewriter/Monospace Text

```latex
\texttt{code\_snippet}
```
Renders text in a fixed-width, monospace font — the standard convention for representing code, commands, file names, or technical tokens inline within regular prose. This is exactly why your documents used it for things like `\texttt{01}` (a specific string being discussed) or `\texttt{amsthm}` (naming a package inline in a sentence).

### 6. Combining Formatting Commands

These can be nested inside one another freely:
```latex
\textbf{\textit{Bold and italic together}}
\textbf{\texttt{Bold monospace text}}
```

### 7. Quick Reference Table

| Command | Effect | Context-aware? |
|---|---|---|
| `\textbf{}` | Bold | No |
| `\textit{}` | Italic | No |
| `\emph{}` | Emphasis (usually italic) | Yes — flips to upright if already in italic context |
| `\underline{}` | Underline | No |
| `\texttt{}` | Monospace/typewriter font | No |

---

## PART 2: Sectioning Hierarchy

### 1. The Full Hierarchy (article class)

```latex
\section{...}
\subsection{...}
\subsubsection{...}
\paragraph{...}
\subparagraph{...}
```
This is the complete depth ladder available in the `article` class, from broadest structural division down to the finest. (Note: `article` does **not** have `\chapter` — that only exists in `report` and `book` classes.)

### 2. What Each Level Does Visually

| Command | Default appearance | Numbered? | In ToC by default? |
|---|---|---|---|
| `\section{}` | Large bold heading, own line | Yes (e.g., "1", "2") | Yes |
| `\subsection{}` | Slightly smaller bold heading | Yes (e.g., "1.1") | Yes |
| `\subsubsection{}` | Smaller still, own line | Yes (e.g., "1.1.1") | Yes (up to default `tocdepth`) |
| `\paragraph{}` | Bold, **run-in** (starts inline with body text, not its own line) | No, by default | No |
| `\subparagraph{}` | Similar to `\paragraph`, often indented further | No, by default | No |

### 3. Automatic Numbering

Every non-starred sectioning command **auto-numbers itself**, incrementing based on its position in the hierarchy:
```latex
\section{Intro}          % → "1  Intro"
\subsection{Background}  % → "1.1  Background"
\subsection{Method}      % → "1.2  Method"
\section{Results}        % → "2  Results" (subsection counter resets to 0 under this new section)
```
Numbers reset for lower levels whenever a higher-level section begins — e.g., starting `\section{Results}` resets the subsection counter back to zero, so the next `\subsection` becomes "2.1", not continuing from "1.3".

### 4. Starred Versions — Unnumbered Variants

```latex
\section*{Unlisted Section}
\subsection*{...}
\subsubsection*{...}
```
The `*` suppresses **both** the automatic number **and** the table-of-contents entry. This was demonstrated in your documents:
```latex
\section*{Unlisted Section}
This section uses an asterisk to prevent it from appearing in the Table of Contents.
```
```latex
\subsubsection*{Important observation}
```
Use this when you want a heading-styled break in the text without it counting as part of the formal numbered structure — like an aside, a disclaimer, or an "Acknowledgments" section that doesn't need a number.

### 5. Manually Adding a Starred Section to the ToC Anyway

If you want the visual "unnumbered" look but still want it listed in the table of contents, you can force an entry manually:
```latex
\section*{Unlisted Section}
\addcontentsline{toc}{section}{Unlisted Section}
```
`\addcontentsline{toc}{section}{...}` manually inserts a line into the `.toc` file at the "section" level, with the given text — bypassing the usual auto-numbering mechanism entirely.

### 6. `\paragraph{}` and `\subparagraph{}` — Run-In Behavior (recap)

As covered earlier in our conversation:
```latex
\paragraph{Key Point} This text immediately follows on the same line.
```
Unlike `\section`/`\subsection`/`\subsubsection` (which always start a fresh line), `\paragraph{}` prints its title bolded and **inline**, with the subsequent body text flowing right after it on the same line — more like a labeled topic sentence than a standalone heading.

### 7. `\part{}` — One Level Above `\section` (bonus, not in your docs)

```latex
\part{Part One: Foundations}
```
Exists in `article`/`report`/`book` as an even higher structural division than `\section`, used for splitting a very long document into major parts (e.g., "Part I", "Part II"). Rarely used in short reports, but worth knowing it exists at the top of the hierarchy.

### 8. Controlling How Deep Numbering Goes — `secnumdepth`

Similar to the earlier `tocdepth` counter, there's a parallel counter that controls how deep **numbering** applies (independent of what shows in the ToC):
```latex
\setcounter{secnumdepth}{2}
```
With this set to `2`, `\subsubsection` headings would still print, but **without** an automatic number in front of them (since subsubsection is depth-level 3, beyond the depth-2 cutoff) — even though they weren't explicitly starred.

### 9. Quick Reference Table

| Syntax | Purpose |
|---|---|
| `\section{}` → `\subparagraph{}` | Full sectioning hierarchy, deepest to shallowest already covered |
| `*` suffix (e.g. `\section*{}`) | Suppresses numbering and ToC entry |
| `\addcontentsline{toc}{level}{text}` | Manually force a ToC entry for a starred/unnumbered heading |
| `\part{}` | Structural level above `\section` |
| `\setcounter{secnumdepth}{N}` | Controls how deep automatic numbering applies |
| `\setcounter{tocdepth}{N}` | (recap) Controls how deep the ToC listing goes |

---

Ready when you want **line break commands** and the **`multicol` package** next — just say the word.
## PART 3: Line Break Commands

### 1. `\\` — The Basic Line Break

```latex
This is line one. \\
This is line two.
```
Forces a line break at that exact point, **without** starting a new paragraph. Visually, the text continues immediately below with no extra vertical spacing and no first-line indent — unlike a blank line (which creates a true new paragraph, as covered earlier).

**Where `\\` is used differently across contexts you've already seen:**
- In `align`/`equation` environments: ends a row/line of the math derivation
- In `tabular`: ends a row of the table
- In plain body text: forces a manual line break within the same paragraph
- In `\title{}`: forces a line break between the title and a subtitle

```latex
\title{Main Title\\\large Subtitle Line}
```

### 2. `\\` with an Optional Spacing Argument

```latex
Line one. \\[10pt]
Line two, with extra vertical gap before it.
```
`\\[length]` adds **extra vertical space** after the break, on top of the normal single-line gap — commonly used in tables or math blocks where you want a bit of breathing room between specific rows without adding a full blank line.

### 3. `\newline`

```latex
This is one line.\newline
This continues on a new line.
```
Functionally very similar to `\\` in plain text — forces a line break without starting a new paragraph. The practical difference: `\newline` is meant specifically for body text and **cannot** take the optional spacing argument (`\newline[10pt]` is not valid), and it also can't be used inside things like `tabular` the way `\\` can (since `\\` in a table has the special "end of row" meaning that `\newline` doesn't replicate).

### 4. `\linebreak`

```latex
This is a paragraph that continues normally until \linebreak this point, where it breaks.
```
This is a subtly different tool from `\\`/`\newline`. Rather than *forcing* an immediate cut, `\linebreak` tells LaTeX "please break the line here" while still trying to **justify** the line nicely — stretching the inter-word spacing on that line so the text still reaches the right margin, rather than leaving a visibly short, ragged line like `\\` typically produces.

**Optional strength argument** (same pattern as `\pagebreak` from earlier):
```latex
\linebreak[4]   % strongest — default, essentially forces the break
\linebreak[0]   % weakest — just a hint, easily ignored by LaTeX's algorithm
```

### 5. `\\` vs. `\newline` vs. `\linebreak` — Practical Comparison

| Command | Justifies the line before breaking? | Works inside `tabular`/`align`? | Optional spacing argument? |
|---|---|---|---|
| `\\` | No — leaves a ragged/short line | Yes (this is its primary role there) | Yes, `\\[length]` |
| `\newline` | No | No | No |
| `\linebreak` | Yes — stretches spacing to justify | No (not its intended use) | Yes, strength `[0–4]` |

**Practical takeaway:** for everyday manual line breaks in body text, `\\` and `\newline` are functionally interchangeable and far more common in practice; `\linebreak` is a more specialized tool reached for when you specifically care about the outgoing line still looking justified rather than short/ragged.

### 6. Important Distinction — None of These Start a New Paragraph

Worth re-emphasizing since it connects back to our very early `\paragraph{}` discussion: `\\`, `\newline`, and `\linebreak` all just move to the next line visually. They do **not** reset paragraph-level formatting (like first-line indent) or add the vertical spacing a true new paragraph gets. Only a blank line (or explicit `\par`) creates an actual new paragraph.

```latex
First line.\\
Second line, same paragraph, no indent.

Third section - blank line above means this IS a new paragraph, may get an indent.
```

---

## PART 4: The `multicol` Package

### 1. Loading the Package

```latex
\usepackage{multicol}
```
This is loaded in your fourth document's preamble but never actually used in the body — so let's cover what it *would* do.

### 2. The Core Difference From `twocolumn`

This is the key distinction to understand: `\documentclass[twocolumn]{article}` makes the **entire document** two-column, from title to end. `multicol` instead lets you create a multi-column **block within an otherwise normal single-column document** — you can freely switch in and out of multi-column layout wherever you like.

### 3. The `multicols` Environment

```latex
\begin{multicols}{3}
Text placed here will automatically flow across three columns, wrapping from
the bottom of one column to the top of the next, then continuing normally
once the environment ends.
\end{multicols}
```
The mandatory argument (`{3}` here) sets the **number of columns** for just this block.

### 4. Key Behavioral Difference From `twocolumn`

- In `twocolumn` mode, the two columns are **balanced by page breaks** — content fills the left column of a page fully before flowing to the right column
- In `multicols`, by default LaTeX tries to **balance the columns evenly** at the *end* of the block specifically — meaning if the content doesn't perfectly fill whole columns, LaTeX will try to make all columns roughly the same height rather than filling the first column completely before starting the next

### 5. Column Separation and Rule Options

```latex
\setlength{\columnsep}{1cm}      % gap between columns
\setlength{\columnseprule}{0.4pt}  % draws a thin vertical line between columns
```
These lengths control the visual spacing/dividers between columns — `\columnsep` is the gap width, and `\columnseprule` (if set to a nonzero value) draws a vertical rule down the middle of that gap. By default, `\columnseprule` is `0pt` (no visible line).

### 6. Forcing a Column Break Manually

```latex
\begin{multicols}{2}
Text in column one...
\columnbreak
Text forced to start at the top of column two...
\end{multicols}
```
`\columnbreak` works analogously to `\newpage`, but at the column level instead of the page level — forces whatever follows to start at the top of the next column, rather than letting LaTeX decide where the natural break falls.

### 7. Practical Example

```latex
\usepackage{multicol}

\begin{document}

Normal single-column introduction text goes here, spanning the full page width...

\begin{multicols}{2}
This section discusses two related but distinct points side by side in a
compact two-column format, useful for glossaries, short comparative notes,
or dense reference material.

\columnbreak

This is forced into the second column early, rather than waiting for the
natural balance point.
\end{multicols}

Back to normal single-column text for the rest of the document.

\end{document}
```

### 8. When to Use `multicol` vs. `twocolumn`

| Scenario | Best tool |
|---|---|
| Entire academic paper/newsletter in columns | `\documentclass[twocolumn]{article}` |
| Just one section (glossary, short list, references) needs columns | `multicol` package, `multicols` environment |
| Need a wide figure/table spanning both columns in a `twocolumn` doc | `figure*`/`table*` (only works in true `twocolumn` mode, not inside `multicols`) |

### Quick Reference Table

| Syntax | Purpose |
|---|---|
| `\\` | Manual line break, no justification, works in tables/math too |
| `\\[length]` | Line break with extra vertical spacing added |
| `\newline` | Manual line break, body text only |
| `\linebreak[0-4]` | Line break with justification maintained on the outgoing line |
| `\begin{multicols}{n}...\end{multicols}` | Multi-column block within a single-column document |
| `\columnbreak` | Force a break to the next column within `multicols` |
| `\setlength{\columnsep}{}` | Set gap width between columns |
| `\setlength{\columnseprule}{}` | Draw a vertical rule between columns |

That covers everything from your four uploaded documents. Let me know if you'd like a consolidated review of any topic, or if there's something new you want to dig into.


Let's cover these three — the `comment` package, horizontal/vertical spacing commands, and image manipulation options.

## PART 1: The `comment` Package

### 1. The Problem It Solves

You already know `%` comments out a single line. But what if you want to disable a **large block** of text/code temporarily — say, 30 lines — without putting `%` in front of every single one? That's exactly what `comment` is for.

```latex
\usepackage{comment}
```

### 2. The `comment` Environment

```latex
\begin{comment}
This entire block is completely ignored by LaTeX.
It can span as many lines as you want.
\section{This won't even be processed as a section}
\includegraphics{ignored-image.png}
\end{comment}
```
Everything between `\begin{comment}` and `\end{comment}` is **entirely skipped** — not just visually hidden, but not processed by the compiler at all. This means even broken/invalid LaTeX code inside a `comment` block won't cause compile errors, since LaTeX never actually tries to interpret it.

### 3. `comment` vs. `%` — Key Differences

| | `%` (built-in) | `comment` environment (package) |
|---|---|---|
| Scope | One line only | Any number of lines |
| Needs a package? | No | Yes, `\usepackage{comment}` |
| Speed to toggle | Must add/remove `%` on every line | Just wrap/unwrap one `\begin{comment}...\end{comment}` block |
| Common use | Quick one-line notes or disabling a single line | Disabling large sections (e.g., a whole figure, a whole paragraph, draft text) |

### 4. Practical Example — Comparing Both Approaches

**Using `%` line-by-line** (what your very first document actually did):
```latex
% \begin{figure}
%     \centering
%     \includegraphics[width=0.95\linewidth]{a.png}
%     \caption{This is an example...}
%     \label{fig:trump}
% \end{figure}
```
Every line needs its own `%` — tedious for large blocks, and easy to accidentally miss one line when toggling it back on.

**Using the `comment` environment instead** (functionally equivalent, less typing):
```latex
\begin{comment}
\begin{figure}
    \centering
    \includegraphics[width=0.95\linewidth]{a.png}
    \caption{This is an example...}
    \label{fig:trump}
\end{figure}
\end{comment}
```
Only two lines added/removed to toggle the whole block on or off.

### 5. Defining Your Own "Excluded" Environments (advanced, bonus)

```latex
\usepackage{comment}
\excludecomment{draftnotes}
```
This creates a **custom-named** comment-like environment. Anything inside `\begin{draftnotes}...\end{draftnotes}` is excluded from the compiled output — useful when you want a semantically meaningful name (like marking personal notes vs. temporarily disabled code) rather than always calling it generic "comment."

```latex
\begin{draftnotes}
Remember to double check this citation before submission.
\end{draftnotes}
```

There's also the inverse, `\includecomment{name}`, which makes a custom environment **actively include** its content — letting you flip a whole named block on/off by changing just one line in the preamble (`\excludecomment` vs `\includecomment`) rather than hunting through the document.

---

## PART 2: Horizontal (and Vertical) Spacing

### 1. `\hfill` — Flexible Horizontal Space

```latex
\includegraphics[width=0.28\linewidth]{img1.jpg}
\hfill
\includegraphics[width=0.28\linewidth]{img2.jpg}
```
`\hfill` inserts **stretchable** horizontal space that expands to fill whatever room is available — it doesn't have a fixed size, it grows or shrinks based on context. This is why it's the go-to tool for evenly spacing multiple images or subfigures across a line: however much room is left over gets distributed into that gap automatically.

**Common uses:**
```latex
Left text \hfill Right text          % pushes text to opposite ends of the line
```
```latex
\begin{subfigure}{0.45\linewidth} ... \end{subfigure}
\hfill
\begin{subfigure}{0.45\linewidth} ... \end{subfigure}
```

### 2. `\hspace{}` — Fixed Horizontal Space

```latex
Some text\hspace{1cm}more text
```
Unlike `\hfill`, this inserts an **exact, fixed-width** gap — it doesn't stretch or shrink based on available space. Any standard length unit works: `cm`, `mm`, `in`, `pt`, `em`, `ex`.

**`\hspace*{}` — the starred variant:**
```latex
\hspace*{1cm}Indented text
```
Normally, LaTeX can suppress horizontal space at the very start/end of a line if it's not needed for the layout. The starred version **forces** the space to appear regardless — useful specifically when you need guaranteed indentation at the beginning of a line, which plain `\hspace` might otherwise silently discard.

### 3. Math-Mode Spacing Commands (recap from the math lesson)

```latex
\,   % thin space
\:   % medium space
\;   % thick space
\quad    % width of one "em" (roughly the width of a capital M)
\qquad   % double \quad, extra wide
```
These are specifically meant for fine-tuning spacing **within math expressions** — e.g., `a \qquad b` in your very first document's size demo used this to create a visibly wide gap between two variables in math mode.

### 4. `\vspace{}` — Fixed Vertical Space

```latex
\vspace{0.35cm}
```
The vertical counterpart to `\hspace{}` — inserts a fixed-height blank gap, typically used between paragraphs, before/after a figure, or (as seen in your document) to create a gap between rows of subfigures:
```latex
\begin{subfigure}{0.45\linewidth} ... \end{subfigure}
\hfill
\begin{subfigure}{0.45\linewidth} ... \end{subfigure}
\vspace{0.35cm}
\begin{subfigure}{0.45\linewidth} ... \end{subfigure}
```

**`\vspace*{}` — starred version:**
Just like `\hspace*{}`, this forces the vertical space to appear even at a position (like the top of a page) where LaTeX might otherwise try to suppress it.

### 5. `\vfill` — Flexible Vertical Space (bonus, parallel to `\hfill`)

```latex
Text at the top
\vfill
Text pushed to the bottom of the page
```
Stretches to fill all remaining vertical space on the page — useful for pushing content to the very bottom (like a signature line or footer-like text) regardless of how much content precedes it.

### 6. Quick Comparison Table

| Command | Direction | Fixed or flexible? |
|---|---|---|
| `\hspace{length}` | Horizontal | Fixed |
| `\hspace*{length}` | Horizontal | Fixed, forced even at line edges |
| `\hfill` | Horizontal | Flexible, fills available space |
| `\vspace{length}` | Vertical | Fixed |
| `\vspace*{length}` | Vertical | Fixed, forced even at page edges |
| `\vfill` | Vertical | Flexible, fills available space |
| `\,` `\:` `\;` `\quad` `\qquad` | Horizontal (math mode) | Fixed, small-to-large increments |

---

## PART 3: Image Manipulation — `\includegraphics` Options (full detail)

You've touched this before in the figures lesson, so here's the complete, consolidated picture of every sizing/transform option.

### 1. `width=`

```latex
\includegraphics[width=0.5\linewidth]{image.png}
\includegraphics[width=5cm]{image.png}
```
Scales the image so its width matches the given value. Using `\linewidth` (or `\textwidth`) as a relative unit is preferred over fixed units like `cm`, because it automatically adapts if the surrounding text width changes (e.g., different margins, two-column layout, a `wrapfigure`'s narrower slot, etc.) — a hardcoded `cm` value won't adjust and might look wrong in a different context.

### 2. `height=`

```latex
\includegraphics[height=4cm]{image.png}
```
Scales the image so its height matches the given value instead. Less commonly used than `width=`, since page width is usually the more predictable constraint (page height can vary based on how much text precedes the image).

### 3. Specifying Both `width=` and `height=` Together

```latex
\includegraphics[width=5cm, height=3cm]{image.png}
```
⚠️ By default, this **distorts** the image — it stretches/squishes it to force both dimensions exactly, ignoring the image's natural aspect ratio. To scale proportionally while still constraining both dimensions (i.e., "fit within this box, keep proportions"), add `keepaspectratio`:
```latex
\includegraphics[width=5cm, height=3cm, keepaspectratio]{image.png}
```
This scales the image to fit **within** the given box without distortion, only filling one of the two dimensions completely (whichever comes first at the correct aspect ratio).

### 4. `scale=`

```latex
\includegraphics[scale=0.5]{image.png}
\includegraphics[scale=2]{image.png}
```
Resizes the image by a **multiplicative factor** of its original, native resolution/size — `0.5` = half size, `2` = double size. Unlike `width=`/`height=`, this doesn't reference the surrounding text layout at all, just the image file's own intrinsic dimensions — meaning it's less "responsive" to layout changes than `width=\linewidth`.

### 5. `angle=`

```latex
\includegraphics[angle=8]{image.png}
\includegraphics[angle=-90]{image.png}
\includegraphics[angle=180]{image.png}
```
Rotates the image by the given number of degrees, **counter-clockwise** for positive values, clockwise for negative values. Rotation happens around the image's center by default.

### 6. `origin=` — Changing the Rotation Pivot Point (bonus, pairs with `angle=`)

```latex
\includegraphics[angle=45, origin=c]{image.png}
```
Controls which point the rotation pivots around: `c` (center, the default), `l`/`r` (left/right edge), `t`/`b` (top/bottom edge), or combinations like `tl` (top-left corner).

### 7. `trim=` and `clip` — Cropping an Image

```latex
\includegraphics[trim=1cm 0.5cm 1cm 0.5cm, clip, width=0.5\linewidth]{image.png}
```
`trim={left} {bottom} {right} {top}` specifies how much to cut off each edge (in that specific order), and `clip` is **required** to actually apply the cropping (without `clip`, the `trim` values are calculated but the image isn't visually cut — the extra space is just repositioned).

### 8. Combining Multiple Options

```latex
\includegraphics[width=0.28\linewidth, angle=8]{images/smoke_satellite.png}
```
As seen in your document — any number of these options can be combined, comma-separated, in a single `[ ]` block.

### 9. Quick Reference Table

| Option | Effect |
|---|---|
| `width=` | Scale to a specific width (relative units like `\linewidth` preferred) |
| `height=` | Scale to a specific height |
| `width=` + `height=` (no `keepaspectratio`) | Distorts the image to fit both exactly |
| `keepaspectratio` | Preserves natural proportions when both width/height given |
| `scale=` | Resize by a multiplicative factor of native size |
| `angle=` | Rotate by degrees (positive = counter-clockwise) |
| `origin=` | Set the pivot point for rotation |
| `trim=l b r t` + `clip` | Crop the image by specified margins on each side |

Let me know what's next.

Ah, my mistake — you meant **lists** (itemize/enumerate), not code **listings**. Let's do that properly, covering everything that appeared across your documents plus the full surrounding syntax.

## 1. The Three Built-In List Environments

LaTeX has three core list types out of the box (no extra package needed):

```latex
\begin{itemize}     % bullet points, unordered
    \item First point
    \item Second point
\end{itemize}

\begin{enumerate}     % numbered, ordered
    \item First step
    \item Second step
\end{enumerate}

\begin{description}     % term–definition pairs
    \item[Term A] Definition of term A.
    \item[Term B] Definition of term B.
\end{description}
```

Each `\item` starts a new bullet/number/term. Text after `\item` (up to the next `\item` or `\end{...}`) belongs to that entry, and can span multiple lines/sentences without needing a blank line.

## 2. Default Rendering

- `itemize` → filled bullet (•) at the top level
- `enumerate` → arabic numbers with a period: `1.`, `2.`, `3.`
- `description` → bold term, followed by the definition text run-in on the same line

## 3. Nesting (up to 4 levels deep by default)

Both `itemize` and `enumerate` can be nested inside each other or themselves, and LaTeX **automatically changes the marker style at each depth** so levels are visually distinguishable:

```latex
\begin{itemize}
    \item Level 1 — filled bullet •
    \begin{itemize}
        \item Level 2 — en-dash –
        \begin{itemize}
            \item Level 3 — asterisk *
            \begin{itemize}
                \item Level 4 — centered dot ·
            \end{itemize}
        \end{itemize}
    \end{itemize}
\end{itemize}
```

```latex
\begin{enumerate}
    \item Level 1 — 1, 2, 3...
    \begin{enumerate}
        \item Level 2 — (a), (b), (c)...
        \begin{enumerate}
            \item Level 3 — i, ii, iii...
            \begin{enumerate}
                \item Level 4 — A, B, C...
            \end{enumerate}
        \end{enumerate}
    \end{enumerate}
\end{enumerate}
```

**Mixing types while nesting** (also valid, seen in your third document):
```latex
\begin{enumerate}
    \item Simulation Steps
    \begin{itemize}
        \item Monitor CPU usage
        \item Record intermediate results
    \end{itemize}
\end{enumerate}
```
An `itemize` block nested inside an `enumerate` (or vice versa) just uses its own default marker style at that depth — no conflict.

## 4. Per-Item Custom Labels — `\item[...]`

Every `\item` optionally accepts a bracketed argument that **replaces its default marker just for that one entry**:

```latex
\begin{itemize}
    \item Normal bullet
    \item[--] Dash instead of bullet
    \item[\textbf{Note:}] Bold custom label
    \item[$\star$] A math symbol as the marker
    \item[42.] Manually numbered, even inside itemize
\end{itemize}
```
This is exactly how `description` actually works under the hood — `\item[Term]` is just this same optional-argument mechanism, used to display the "term" as the marker.

From your document:
```latex
\item [--] second item
\item [\textbf{Description}] This is the description
\item[!] Verify connections
\item[$\rightarrow$] Record intermediate results
\item[\#] Document observations
```
All of these individually override just that one bullet/number, while the rest of the list keeps its normal automatic marker.

## 5. Customizing Entire List Styles — `enumitem` Package

Without `enumitem`, changing marker style for an *entire* list (not just one item) requires clunky low-level counter redefinitions. `enumitem` makes it a simple optional argument on `\begin{...}`:

```latex
\usepackage{enumitem}
```

**Enumerate label patterns:**
```latex
\begin{enumerate}[label=\arabic*.]      % 1.  2.  3.
\begin{enumerate}[label=(\alph*)]       % (a) (b) (c)
\begin{enumerate}[label=(\Alph*)]       % (A) (B) (C)
\begin{enumerate}[label=\roman*.]       % i.  ii.  iii.
\begin{enumerate}[label=(\roman*)]      % (i) (ii) (iii)
\begin{enumerate}[label=Step \arabic*:] % Step 1:  Step 2:  Step 3:
```
The `*` inside `label=` is a placeholder that `enumitem` substitutes with the running counter value at each `\item`. `\arabic`, `\alph`, `\Alph`, `\roman`, `\Roman` are the counter-formatting commands (lowercase vs. uppercase letters/numerals).

**Itemize custom bullet/symbol for the whole list:**
```latex
\begin{itemize}[label=-]           % dash for every item
\begin{itemize}[label=\textbullet] % explicit bullet character
\begin{itemize}[label=$\rightarrow$]  % arrow symbol for every item
```

**Spacing controls (also from `enumitem`):**
```latex
\begin{itemize}[itemsep=4pt]        % extra vertical space between items
\begin{itemize}[topsep=0pt]         % space before the list starts
\begin{itemize}[leftmargin=1.5cm]   % indent the whole list further
\begin{itemize}[noitemsep]          % remove extra spacing (compact list)
```
These can be combined, comma-separated:
```latex
\begin{itemize}[label=--, itemsep=2pt, leftmargin=2em]
```

**Resuming/continuing a numbered list after interruption:**
```latex
\begin{enumerate}
    \item First
    \item Second
\end{enumerate}

Some interrupting paragraph...

\begin{enumerate}[resume]
    \item Continues from 3, not restarting at 1
\end{enumerate}
```

**Starting a list at a specific number:**
```latex
\begin{enumerate}[start=5]
    \item This is numbered 5
    \item This is numbered 6
\end{enumerate}
```

## 6. Loose vs. Tight Lists (spacing default behavior)

By default, `itemize`/`enumerate` add a bit of vertical breathing room between items and before/after the whole block. If you want a tighter, more compact list (common in slides or dense reports):

```latex
\begin{itemize}[noitemsep, nolistsep]
    \item Compact
    \item List
\end{itemize}
```

## 7. Lists Without Any Bullets/Numbers at All

Sometimes you just want indentation with no marker:
```latex
\begin{itemize}[label={}]
    \item This line is indented like a list item but has no visible bullet.
\end{itemize}
```

## 8. Full Worked Example (combining everything)

```latex
\usepackage{enumitem}

\begin{enumerate}[label=\arabic*.]
    \item System Setup
    \begin{itemize}[label=--]
        \item Install software packages
        \item Configure hardware
        \item[!] Verify connections
    \end{itemize}

    \item Simulation Steps
    \begin{enumerate}[label=(\alph*)]
        \item Initialize parameters
        \item Run simulation
        \begin{itemize}[label=-]
            \item Monitor CPU usage
            \item[$\rightarrow$] Record intermediate results
        \end{itemize}
        \item Analyze outputs
    \end{enumerate}

    \item Reporting
    \begin{itemize}
        \item Prepare charts
        \item[\#] Document observations
        \item Summarize findings
        \begin{enumerate}[label=(\alph*)]
            \item Key insights
            \item Recommendations
        \end{enumerate}
    \end{itemize}
\end{enumerate}
```
— this is essentially exactly what appeared in your third document, and demonstrates: custom top-level numbering, nested bullets, per-item symbol overrides, and mixed enumerate/itemize nesting all in one structure.

## 9. Quick Reference Table

| Syntax | Effect |
|---|---|
| `\begin{itemize}` | Unordered bullet list |
| `\begin{enumerate}` | Ordered numbered list |
| `\begin{description}` | Term/definition list |
| `\item` | New entry, default marker |
| `\item[X]` | New entry, marker overridden to `X` for just this item |
| `[label=...]` | (enumitem) overrides marker style for the **whole** list |
| `[itemsep=...]`, `[topsep=...]` | (enumitem) spacing controls |
| `[resume]` | (enumitem) continue numbering from a previous list |
| `[start=N]` | (enumitem) begin numbering at N |
| Nesting `itemize`/`enumerate` inside `\item` | Automatic marker style change per depth (up to 4 levels) |

Let me know what's next.

Math typesetting is one of LaTeX's biggest strengths — let's cover it fully, from basic inline math through multi-line derivations and formal proofs.

## 1. Two Fundamental Modes: Inline vs. Display

**Inline math** — flows within a sentence, stays on the same line as surrounding text:
```latex
The variable $x$ satisfies $x^2 = 4$.
The set is defined as \(\Sigma = \{0, 1\}\).
```
Two equivalent syntaxes: `$...$` (classic, plain-TeX style) and `\(...\)` (modern LaTeX style). Both do the same thing — `\(...\)` is generally considered more "correct" LaTeX, but `$...$` is extremely common and totally fine.

**Display math** — centered on its own line, visually separated from the paragraph, usually rendered larger:
```latex
$$ x^2 + y^2 = z^2 $$

\[ x^2 + y^2 = z^2 \]
```
Again, two equivalent syntaxes. `$$...$$` is plain-TeX (technically discouraged in "pure" LaTeX because it can cause subtle spacing bugs in rare cases), while `\[...\]` is the modern LaTeX-native equivalent — functionally identical for almost all everyday purposes.

**Key visual difference between inline and display:**
```latex
The formula $\frac{1}{2}$ inline vs. $$\frac{1}{2}$$ displayed
```
Inline fractions/sums/etc. get compressed to fit the line height; display versions render at full, more readable size.

## 2. The `equation` Environment — Numbered Display Math

```latex
\begin{equation}
    X = Y_{ij} + Z^{35}
\end{equation}
```
Functionally like `\[...\]`, but **automatically numbers** the equation (e.g., "(1)") flush against the right margin. This number auto-increments across the whole document and can be cross-referenced:
```latex
\begin{equation}
    \label{eq:pythagorean}
    a^2 + b^2 = c^2
\end{equation}

As shown in Equation~\ref{eq:pythagorean}, ...
```
Same rule as figures: `\label{}` should go *inside* the environment, and the two-compile rule applies for `\ref{}` to resolve correctly.

## 3. `equation*` — Unnumbered Version

```latex
\begin{equation*}
    a = \frac{cd}{ef}
\end{equation*}
```
Same as `equation`, but the `*` suppresses the number — same starring convention you've seen with `section*`, `figure*`, etc. Requires the `amsmath` package (plain `equation` is built-in, but `equation*` needs `amsmath`).

## 4. The `align` Environment — Multi-Line Equations

This is for when a derivation or system needs multiple lines, aligned at a common point (usually the `=` sign). Requires `amsmath`.

```latex
\begin{align}
    x_1 &= \frac{-b+\sqrt{b^2-4ac}}{2a} \\
    x_2 &= \frac{-b-\sqrt{b^2-4ac}}{2a}
\end{align}
```

**Key syntax pieces:**
- `&` marks the **alignment point** — every line's `&` lines up vertically with every other line's `&`. Conventionally placed right before the `=` sign.
- `\\` ends a line and starts the next
- By default, `align` numbers **every single line** separately: (1), (2), etc.

**Suppressing the number on one specific line** — `\nonumber`:
```latex
\begin{align}
    x &= a + a + b + c + c + c \nonumber \\
    &= 2a + b + 3c
\end{align}
```
Here the first line shows no number (it's just an intermediate step), while the second line gets numbered — useful when you don't want to reference every single step, only the final result.

**`align*` — fully unnumbered version:**
```latex
\begin{align*}
    x_1 &= \frac{-b+\sqrt{b^2-4ac}}{2a} \\
    x_2 &= \frac{-b-\sqrt{b^2-4ac}}{2a}
\end{align*}
```
No line gets a number at all — common when showing a derivation where the individual steps don't need referencing.

**Chained derivation example** (each line simplifies the previous):
```latex
\begin{align*}
    1+2+\cdots+n
        &= \sum_{i=1}^{n} i \\
        &= \frac{n(n+1)}{2} \\
        &= \frac{n^2 + n}{2} \\
        &= \frac{1}{2} \cdot n \cdot (n+1).
\end{align*}
```
Notice the first line has content **before** the `&`, and every subsequent line starts empty before its `&` — this makes it look like one continuous chain of equalities, each new line picking up right where the last left off, all aligned at `=`.

## 5. Other Multi-Line Math Environments (worth knowing, even if not in your docs)

```latex
\begin{gather}     % centers each line independently, no alignment point needed
    a = b + c \\
    x = y - z
\end{gather}
```
```latex
\begin{multline}   % for a single long equation that needs to wrap across lines
    a + b + c + d + e + f + g \\
    = h + i + j
\end{multline}
```

## 6. Piecewise Functions — `cases`

```latex
$$|x| = \begin{cases}
    x & \text{if } x \geq 0 \\
    -x & \text{if } x < 0
\end{cases}$$
```
- Also requires `amsmath`
- Same `&` alignment and `\\` line-break syntax as `align`
- `\text{...}` lets you insert normal upright text *inside* math mode (needed because math mode by default italicizes everything, which looks wrong for words like "if")

## 7. Matrices

```latex
$$
W = \begin{bmatrix}
    w_{11} & w_{12} & \cdots & w_{1n} \\
    w_{21} & w_{22} & \cdots & w_{2n} \\
    \vdots & \vdots & \ddots & \vdots \\
    w_{m1} & w_{m2} & \cdots & w_{mn}
\end{bmatrix}
$$
```
- `&` separates **columns** within a row (different meaning from its role as an alignment point in `align`)
- `\\` separates **rows**
- `\cdots` (horizontal dots), `\vdots` (vertical dots), `\ddots` (diagonal dots) — used together to indicate omitted middle entries

**Matrix bracket variants:**
```latex
\begin{matrix} ... \end{matrix}     % no brackets at all
\begin{pmatrix} ... \end{pmatrix}   % ( ) parentheses
\begin{bmatrix} ... \end{bmatrix}   % [ ] square brackets
\begin{vmatrix} ... \end{vmatrix}   % | | vertical bars (determinant notation)
\begin{Bmatrix} ... \end{Bmatrix}   % { } curly braces
```

## 8. Key Math Symbols/Commands Reference

**Fractions, roots, powers:**
```latex
\frac{a}{b}       % fraction
\sqrt{x}          % square root
\sqrt[3]{x}       % cube root (or nth root generally)
x^2               % superscript
x_i               % subscript
x_{ij}            % multi-character subscript needs braces
```

**Greek letters:**
```latex
\alpha \beta \gamma \delta \epsilon \varepsilon \theta \lambda \mu \sigma \Sigma \Delta \Omega
```
(lowercase command = lowercase letter; capitalized command = uppercase letter, e.g. `\Sigma` vs `\sigma`)

**Comparison/logic operators:**
```latex
\leq  \geq  \neq  \approx  \in  \subseteq  \cup  \cap
```

**Big operators:**
```latex
\sum_{i=1}^{n}     % summation with bounds
\prod_{i=1}^{n}    % product
\int_{a}^{b}       % integral
```

**Spacing in math mode:**
```latex
\,   % thin space
\:   % medium space
\;   % thick space
\quad   % wide space
\qquad  % extra wide space
```

**Accents:**
```latex
\hat{y}    % hat, e.g. predicted value
\bar{x}    % bar, e.g. average
\vec{v}    % vector arrow
```

**Custom operators (like `argmax`):**
```latex
\DeclareMathOperator*{\argmax}{arg\,max}
```
Goes in the preamble; makes `\argmax` behave like a built-in operator (upright text, proper spacing), with `*` allowing subscripts to stack below it (e.g. `\argmax_{x}`).

## 9. Theorem / Proof Environments — `amsthm` Package

```latex
\usepackage{amsthm}
```

**Defining custom theorem-like environments** — done once in the preamble:
```latex
\newtheorem{theorem}{Theorem}
\newtheorem{lemma}[theorem]{Lemma}
```
- First `\newtheorem{theorem}{Theorem}`: creates an environment called `theorem`, and the word printed before each instance is "Theorem" — auto-numbered (Theorem 1, Theorem 2, ...)
- Second one, `\newtheorem{lemma}[theorem]{Lemma}`: creates a `lemma` environment, but the `[theorem]` argument makes it **share the same counter** as `theorem` — so numbering continues across both types instead of each restarting from 1. If Theorem 1 appears, then a Lemma next becomes Lemma 2, not Lemma 1.

**Other common theorem-like environments you might define the same way:**
```latex
\newtheorem{corollary}[theorem]{Corollary}
\newtheorem{definition}{Definition}    % separate counter, its own numbering
\newtheorem{proposition}[theorem]{Proposition}
```

**Using a theorem/lemma environment:**
```latex
\begin{lemma}[Graph Representability]
    \label{lem:tree_representability}
    Every tree $T = (V, E)$ is a Pairwise Compatibility Graph (PCG).
\end{lemma}
```
- The optional `[Graph Representability]` argument adds a custom title after the auto-number: renders as **"Lemma 1 (Graph Representability)."**
- `\label{}` works exactly like with figures/equations — lets you cross-reference this specific lemma elsewhere with `\ref{}`
- The environment typically renders with the keyword bolded and the body text italicized (default `amsthm` style — this can be customized with `\theoremstyle{}`, see below)

**The `proof` environment:**
```latex
\begin{proof}
    Let $T$ be a tree. By definition, there exists a non-negative edge weight
    assignment $w: E \rightarrow \mathbb{R}^+$ and a real interval $[L, U]$
    such that two vertices $u, v \in V$ are adjacent in the PCG if and only
    if the path distance $d_T(u,v)$ falls within $[L, U]$.
\end{proof}
```
- Built into `amsthm` — no need to define it yourself like `theorem`/`lemma`
- Automatically prepends *"Proof."* in italics at the start
- Automatically appends a **QED symbol** (∎, a small hollow or filled square) at the very end, right-aligned
- Not numbered, not counted — there's only ever "Proof." as the label, since a proof directly follows the theorem/lemma it belongs to

**Manually suppressing the QED symbol** (rare, but useful if a proof is intentionally left incomplete):
```latex
\begin{proof}
    This is a partial argument...
    \renewcommand{\qedsymbol}{}
\end{proof}
```

**Changing the theorem style** (optional, affects how the bolded label + body render):
```latex
\theoremstyle{plain}       % bold keyword, italic body (default — used for theorem/lemma)
\theoremstyle{definition}  % bold keyword, upright/normal body (common for definitions)
\theoremstyle{remark}      % italic keyword, upright body, less emphasis (for remarks/notes)

\newtheorem{definition}{Definition}   % will use whichever style was declared most recently before it
```
Place `\theoremstyle{...}` *before* the relevant `\newtheorem{}` declarations in your preamble — it affects every theorem-type environment defined after it, until changed again.

## 10. Full Worked Example (combining everything)

```latex
\usepackage{amsmath, amssymb, amsthm}

\newtheorem{theorem}{Theorem}
\newtheorem{lemma}[theorem]{Lemma}

\begin{document}

The quadratic formula, derived below, gives both roots of $ax^2+bx+c=0$:
\begin{align}
    x &= \frac{-b \pm \sqrt{b^2 - 4ac}}{2a} \nonumber \\
    \label{eq:quadratic}
\end{align}

\begin{theorem}[Fundamental Theorem of Something]
    \label{thm:example}
    For all $x \in \mathbb{R}$, $x^2 \geq 0$.
\end{theorem}

\begin{proof}
    Let $x \in \mathbb{R}$. If $x \geq 0$, then $x \cdot x \geq 0$ trivially.
    If $x < 0$, then $-x > 0$, and $x^2 = (-x)^2 \geq 0$ by the previous case.
    Either way, $x^2 \geq 0$.
\end{proof}

As proven in Theorem~\ref{thm:example}, squares are always non-negative.

\end{document}
```

## Quick Reference Table

| Syntax | Purpose |
|---|---|
| `$...$` / `\(...\)` | Inline math |
| `$$...$$` / `\[...\]` | Display math, unnumbered |
| `\begin{equation}` | Display math, numbered, single line |
| `\begin{equation*}` | Display math, unnumbered, single line (needs `amsmath`) |
| `\begin{align}` | Multi-line, aligned at `&`, numbered per line |
| `\begin{align*}` | Same, unnumbered |
| `\nonumber` | Suppress numbering on one line inside `align` |
| `\begin{cases}` | Piecewise function definitions |
| `\begin{bmatrix}` (and variants) | Matrices with different bracket styles |
| `\newtheorem{name}{Label}` | Define a new numbered theorem-like environment |
| `\newtheorem{b}[a]{Label}` | Same, but shares numbering counter with environment `a` |
| `\begin{proof}...\end{proof}` | Auto-labeled "Proof.", auto QED symbol at end |
| `\theoremstyle{plain/definition/remark}` | Controls bold/italic styling of theorem-like environments |

Ready for whenever you want the next topic.

| `\begin{proof}
Since both sides are equal, we conclude that:
\begin{equation*}
    a^2 + b^2 = c^2 \qedhere
\end{equation*}
\end{proof}` |

\renewcommand{\qedsymbol}{Q.E.D.}

Tables are one of the densest topics across your documents — let's cover everything, from the basic `tabular` environment through merged cells, professional rules, and colored tables.

## 1. The Core Building Block — `tabular`

```latex
\begin{tabular}{ | l | c | r | }
\hline
 cell1 & cell2 & cell3 \\
\hline
\end{tabular}
```

`tabular` is **not a float** — it doesn't move around the page and has no built-in caption/label/numbering. It just typesets a grid exactly where you place it. To get numbering, captions, and floating behavior, you wrap it inside a `table` environment (covered later).

## 2. Column Specifiers

The mandatory argument right after `\begin{tabular}` defines how many columns exist and how each one behaves:

```latex
{ | l | c | r | }
```

| Specifier | Meaning |
|---|---|
| `l` | Left-aligned, column width auto-sized to content |
| `c` | Center-aligned, auto-sized |
| `r` | Right-aligned, auto-sized |
| `p{width}` | Fixed-width column, text **wraps** like a paragraph (needs `array` package for extra control, though basic `p{}` works with plain LaTeX too) |
| `\|` | Draws a **vertical line** at that position (not a column itself — just a border) |

**Examples from your documents:**
```latex
\begin{tabular}{|p{0.25\linewidth}|p{0.65\linewidth}|}   % two wrapped columns with borders
\begin{tabular}{llr}                                       % three columns: left, left, right — no borders
\begin{tabular}{clp{0.48\linewidth}}                        % center, left, then a wrapped column
```
Note: you can mix specifier types freely in one table, and borders (`|`) are entirely optional per position — you can have some, none, or all.

## 3. Rows and Cells — `&` and `\\`

```latex
cell1 & cell2 & cell3 \\
```
- `&` separates **columns** within a row
- `\\` ends the current row and starts a new one
- The number of `&`-separated entries per row must match the number of columns declared in the specifier

## 4. Horizontal Lines

```latex
\hline
```
Draws a full-width horizontal line across the table. Typically placed at the top, bottom, and between the header row and body.

```latex
\cline{1-2}
```
Draws a **partial** horizontal line — only under the specified column range (here, columns 1 through 2), leaving column 3 without a line beneath it. Useful when a merged cell below doesn't need a full-width separator.

## 5. Merged Columns — `\multicolumn`

```latex
\multicolumn{2}{|c|}{Merged cols} & bigcellcell6 \\
```
Merges the next **N** columns (first argument, here `2`) into a single cell.
- Second argument `{|c|}` is a **mini column-specifier just for this merged cell** — it overrides the table's default alignment/borders for that spot only (here: centered, with borders on both sides)
- Third argument is the actual cell content
- The row still needs the correct total number of `&`-separated entries — a merged 2-column cell counts as **one** entry, so the rest of the row continues normally afterward

## 6. Merged Rows — `\multirow` (needs the `multirow` package)

```latex
\usepackage{multirow}
```
```latex
\multirow{2}{*}{c23} & c22 & c23 \\
\cline{2-3}
 & c32 & c33 \\
```
- First argument: how many rows to span (here, `2`)
- Second argument: width — `*` means "use natural width" (auto-sized to content), though you could specify a fixed width instead
- Third argument: the cell content
- **Important:** in the rows *below* the first, you must leave that column's slot **empty** (just an `&` with nothing before it) — the merged cell already occupies that space, so you don't repeat content there
- `\cline{2-3}` is typically used here to draw a partial line under the *other* columns, while leaving the merged cell's column without a line splitting it

## 7. Professional Table Rules — `booktabs` Package

```latex
\usepackage{booktabs}
```
This package's whole philosophy is: **avoid vertical lines and heavy full-grid borders**, and use a small set of refined horizontal rules instead — considered the more "publication-quality" look (common in academic papers).

```latex
\begin{tabular}{llr}
    \toprule
    \multicolumn{2}{c}{Architecture Profile} \\
    \cmidrule(r){1-2}
    Network Name & Topology & Parameters (M) \\
    \midrule
    ResNet-50 & CNN & 25.6 \\
    ViT-Base & Transformer & 86.0 \\
    \bottomrule
\end{tabular}
```

| Command | Purpose |
|---|---|
| `\toprule` | Thick rule at the very top of the table |
| `\midrule` | Medium rule separating header from body |
| `\bottomrule` | Thick rule at the very bottom |
| `\cmidrule(r){1-2}` | A partial rule under columns 1–2 only (like `\cline`, but styled to match booktabs); the `(r)` trims a bit off the rule's right edge for visual refinement — `(l)` trims the left, `(lr)` trims both |

**Convention with `booktabs`:** you typically **don't** use `\hline` or vertical `|` borders alongside it — mixing the two styles looks inconsistent. `booktabs` tables usually have zero vertical lines at all, relying purely on whitespace and the horizontal rules to separate columns visually.

## 8. Wrapping a Table for Numbering, Captions, and Floating — `table` Environment

```latex
\begin{table}[H]
    \centering
    \begin{tabular}{llr}
        ...
    \end{tabular}
    \caption{Example table utilizing booktabs for cleaner, publication-ready formatting.}
    \label{tab:table1}
\end{table}
```

This works exactly like `figure` did:
- `table` is the **floating container** (placement specifiers `[h]`, `[t]`, `[b]`, `[p]`, `[H]` all behave identically to how they worked for figures — `[H]` needs the `float` package, same as before)
- `\centering` centers the tabular content horizontally
- `\caption{}` gives it a numbered caption — by convention, table captions go **above** the table (unlike figures, where captions go below) — though this is a style convention, not a hard rule LaTeX enforces
- `\label{}` — again, must come **after** `\caption{}` for the same reason as figures (it captures the current counter value, which isn't finalized until `\caption` runs)
- Cross-referenced the same way: `Table~\ref{tab:table1}`

## 9. `\listoftables`

```latex
\listoftables
```
Parallel to `\listoffigures` and `\tableofcontents` — auto-generates a page listing every table's caption, number, and page, pulled from `\caption{}` inside every `table` environment. Same two-compile requirement to populate correctly.

## 10. Colored Tables — `xcolor` with the `[table]` Option

```latex
\usepackage[table]{xcolor}
```
Plain `xcolor` alone is not enough for table coloring — the `[table]` option specifically loads extra commands that let color interact correctly with `tabular`'s internal row/column structure.

**Alternating row colors (striping):**
```latex
\rowcolors{2}{gray!10}{white}
```
- First argument (`2`): which row to **start** the alternating pattern from (here, row 2 — skipping the header row so it isn't colored)
- Second and third arguments: the two colors to alternate between (`gray!10` = 10% gray, then `white`)
- Placed right before the `tabular` environment it should apply to

**Coloring a single specific cell:**
```latex
\cellcolor{softgreen}Correct
```
Placed *inside* a specific cell (before its text), overriding whatever the row-coloring pattern set for that one cell.

**Color-mixing syntax** (from `xcolor`, usable anywhere, not just tables):
```latex
gray!10       % 10% gray, 90% white
teal!70!black % 70% teal, 30% black
```
The `!` syntax blends two colors by percentage — `color!X` blends with white by X%, and `color!X!othercolor` blends two named colors by X%.

**Custom color definitions** (used to create the colors referenced above):
```latex
\definecolor{softgreen}{RGB}{226, 245, 236}
\definecolor{softred}{RGB}{255, 232, 232}
\definecolor{softyellow}{RGB}{255, 247, 214}
```
Defined once in the preamble, then reusable by name anywhere in the document (tables, text, boxes, etc.).

## 11. Full Worked Example (combining everything)

```latex
\usepackage{graphicx}
\usepackage{float}
\usepackage{multirow}
\usepackage{booktabs}
\usepackage[table]{xcolor}

\definecolor{softgreen}{RGB}{226, 245, 236}
\definecolor{softred}{RGB}{255, 232, 232}

\begin{table}[H]
    \centering
    \rowcolors{2}{gray!10}{white}
    \begin{tabular}{|l|c|c|}
        \hline
        \multirow{2}{*}{Model} & \multicolumn{2}{c|}{Performance} \\
        \cline{2-3}
         & Accuracy & \cellcolor{softgreen}Speed \\
        \hline
        ResNet-50 & 76.1\% & Fast \\
        \hline
        ViT-Base & \cellcolor{softred}81.2\% & Slow \\
        \hline
    \end{tabular}
    \caption{Comparison of two model architectures.}
    \label{tab:model-comparison}
\end{table}

As shown in Table~\ref{tab:model-comparison}, the two architectures trade off
accuracy against speed.
```

## Quick Reference Table

| Syntax | Purpose |
|---|---|
| `\begin{tabular}{l/c/r/p{}...}` | Core grid environment; column specifiers control alignment/width |
| `&` | Separate columns within a row |
| `\\` | End current row |
| `\hline` | Full-width horizontal line |
| `\cline{a-b}` | Partial horizontal line under columns a–b |
| `\multicolumn{n}{spec}{content}` | Merge n columns into one cell |
| `\multirow{n}{width}{content}` (needs `multirow`) | Merge n rows into one cell |
| `\toprule` / `\midrule` / `\bottomrule` / `\cmidrule` (needs `booktabs`) | Clean, publication-style horizontal rules |
| `\begin{table}[H/h/t/b/p]` | Floating container: adds numbering, caption, cross-referencing |
| `\caption{}` (above table, by convention) | Numbered caption |
| `\label{}` (after `\caption`) | Cross-reference tag |
| `\listoftables` | Auto-generated list of all table captions |
| `\rowcolors{start}{color1}{color2}` (needs `xcolor[table]`) | Alternating row stripe colors |
| `\cellcolor{color}` | Color one specific cell |
| `\definecolor{name}{RGB}{r,g,b}` | Define a reusable custom color |

Let me know what's next.

Let's cover these three — `\verb`/`verbatim`, the `geometry` package, and two-column documents.

## PART 1: `\verb` and the `verbatim` Environment

### 1. The Core Idea

Both of these exist for one purpose: printing text **exactly as typed**, with no LaTeX commands interpreted — including backslashes, braces, special characters, spacing, and line breaks all shown literally. This is essential when you want to *display* LaTeX code itself (or any code) rather than *execute* it.

### 2. `\verb` — Inline Literal Text

```latex
\verb|\textbf{...}|
```
Renders literally as: `\textbf{...}` (unexecuted, shown as plain text in monospace font).

**Syntax mechanics — the delimiter trick:**
```latex
\verb|content here|
```
Unlike most LaTeX commands, `\verb` doesn't use `{ }` as its argument delimiter. Instead, it uses **whatever character immediately follows it** as the delimiter, and expects that same character to close it. This is necessary because the content itself might contain braces (like `\textbf{...}`), which would otherwise confuse LaTeX's normal brace-matching.

```latex
\verb|\textbf{bold}|      % using | as delimiter
\verb!\textbf{bold}!      % using ! as delimiter — works identically
\verb+\textbf{bold}+      % using + as delimiter — works identically
```
You can pick **any** character as the delimiter, as long as it doesn't appear inside the content itself. `|`, `!`, and `+` are common choices because they rarely appear in code snippets.

**What you can't use as a delimiter:** the character can't be a letter, `*`, or space — and obviously can't be a character that appears in your content, or it would end the `\verb` early.

**Example needing a different delimiter:**
```latex
\verb|a|b|     % BROKEN — this ends at the second |, leaving a stray "b|"
\verb!a|b!     % CORRECT — using ! instead, since | appears in the content
```

**Limitations of `\verb`:**
- Must be entirely on **one line** — cannot contain a line break
- Cannot be used inside the argument of another command (e.g., can't put `\verb` inside a `\footnote{}` directly in most cases)
- Fragile in "moving arguments" (section titles, captions) — may need special handling there

### 3. `verbatim` Environment — Block-Level Literal Text

```latex
\begin{verbatim}
This claim needs a short explanation.\footnote{Write the note here.}
\end{verbatim}
```
Renders exactly as written, including the literal backslash and braces, across **multiple lines**, preserving:
- Line breaks exactly as typed
- Spacing/indentation exactly as typed
- Any special characters (`\`, `{`, `}`, `%`, `$`, etc.) with no need to escape them

**Used in your document to show LaTeX code samples without executing them:**
```latex
\begin{verbatim}
@inproceedings{vaswani2017attention,
  author = {Ashish Vaswani and others},
  title = {Attention Is All You Need},
  booktitle = {Advances in Neural Information Processing Systems},
  year = {2017}
}
\end{verbatim}
```
This is why the `.bib` entry syntax, natbib command examples, and bibliography setup commands in your document all appeared as readable literal text rather than being executed as real LaTeX commands.

### 4. Key Differences Between `\verb` and `verbatim`

| | `\verb` | `verbatim` |
|---|---|---|
| Scope | Inline, single line only | Full block, multiple lines |
| Delimiter | Any non-letter character you choose | `\begin{verbatim}`...`\end{verbatim}` |
| Use case | A short snippet within a sentence | A standalone block of code/commands |

### 5. Common Pitfall — Can't Use Inside Certain Commands

```latex
\section{The \verb|\textbf{}| command}   % often causes errors
```
`\verb` (and `verbatim`) generally can't be used inside "fragile" command arguments like section titles or captions, because those get processed in ways that conflict with `\verb`'s special delimiter-scanning mechanism. Workarounds exist (e.g., the `cprotect` package, or restructuring the sentence), but it's simplest to just avoid using `\verb` inside such arguments.

---

## PART 2: The `geometry` Package

### 1. Loading and Basic Usage

```latex
\usepackage[margin=1in]{geometry}
```
This is the standard, modern way to control page margins — much easier than the older manual `\textwidth`/`\topmargin` length-adjustment approach LaTeX used before this package existed.

`margin=1in` sets **all four margins** (top, bottom, left, right) to 1 inch uniformly in one shot.

### 2. Setting Margins Individually

```latex
\usepackage[top=1in, bottom=1in, left=1.25in, right=1.25in]{geometry}
```
Each side can be set independently — useful when, say, you need extra left margin for binding, or different top margin to accommodate a header.

### 3. Other Common Length Units

```latex
\usepackage[margin=2cm]{geometry}
\usepackage[margin=20mm]{geometry}
```
Any standard LaTeX length unit works: `in` (inches), `cm` (centimeters), `mm` (millimeters), `pt` (points).

### 4. Paper Size Options (also handled by `geometry`)

```latex
\usepackage[a4paper, margin=1in]{geometry}
\usepackage[letterpaper, margin=1in]{geometry}
```
While paper size can also be set via `\documentclass[a4paper]{article}`, `geometry` can control it too — and if both are specified, `geometry`'s setting generally takes precedence.

### 5. Additional Layout Controls (bonus, not in your docs but commonly used alongside)

```latex
\usepackage[margin=1in, headheight=15pt, footskip=30pt]{geometry}
```
- `headheight` — space reserved for a page header
- `footskip` — distance from the bottom of the text to the footer
- `landscape` — rotates the whole page layout to landscape orientation

### 6. Changing Geometry Mid-Document (rare, but possible)

```latex
\newgeometry{margin=2in}
...
\restoregeometry
```
`\newgeometry{}` temporarily overrides margins from that point forward; `\restoregeometry` reverts back to whatever was set in the original `\usepackage[...]{geometry}` call. Useful for a single wide page (like a large table or landscape figure) in an otherwise normal-margin document.

---

## PART 3: Two-Column Documents

### 1. Document-Wide Two-Column Layout

```latex
\documentclass[twocolumn]{article}
```
This class option makes the **entire document** flow in two newspaper-style columns side by side, with text wrapping from the bottom of the left column to the top of the right column, then to a new page.

### 2. What Changes Automatically

- Normal `figure` and `table` environments are confined to the width of a **single column** — they won't stretch across both
- Text, paragraphs, lists, and most content just naturally reflow into the narrower column width
- `\maketitle` typically still spans the full page width at the top, before the two-column flow begins

### 3. Spanning Both Columns — `figure*` and `table*`

```latex
\begin{figure*}[t]
    \centering
    \includegraphics[width=0.8\textwidth]{wide-image.png}
    \caption{A figure spanning both columns.}
    \label{fig:wide}
\end{figure*}
```
The **starred** versions (`figure*`, `table*`) span the **full page width**, crossing both columns — useful for wide diagrams, tables, or images that wouldn't fit legibly in a single narrow column.

**Important placement restriction:** starred floats (`figure*`/`table*`) can typically only use `[t]` or `[b]` (top or bottom of the page) — they generally cannot appear in the middle of two-column text, since that would require breaking the column layout mid-page.

### 4. Switching Columns Mid-Document (rare, advanced use)

```latex
\onecolumn
... single-column content here ...
\twocolumn
... back to two-column content ...
```
These commands let you switch layout modes partway through — for example, if you want the abstract or a large figure in single-column format, then switch to two columns for the main body.

### 5. `multicol` Package — Column Layout Without Changing the Whole Document

This is different from `twocolumn`: rather than being a document-class-wide setting, `multicol` lets you create a **multi-column block within an otherwise single-column document**:

```latex
\usepackage{multicol}
...
\begin{multicols}{2}
    Text here will flow into two columns, just within this block...
\end{multicols}
```
(This package was loaded in your fourth document's preamble but never actually used in the body — so it's worth knowing what it *would* do, since it's listed as one of the remaining uncovered topics too.)

### 6. Combining `geometry` and `twocolumn`

```latex
\documentclass[twocolumn]{article}
\usepackage[margin=0.75in]{geometry}
```
Perfectly valid together — `geometry` controls the overall page margins, while `twocolumn` controls how the text within that page area is split. Two-column layouts often use slightly tighter margins than single-column ones, since column width is already constrained.

---

## Quick Reference Table

| Syntax | Purpose |
|---|---|
| `\verb|text|` | Inline literal text, delimiter = any non-letter character |
| `\begin{verbatim}...\end{verbatim}` | Block-level literal text, multi-line |
| `\usepackage[margin=1in]{geometry}` | Set all four page margins uniformly |
| `\usepackage[top=,bottom=,left=,right=]{geometry}` | Set margins individually |
| `\newgeometry{}` / `\restoregeometry` | Temporarily override margins mid-document |
| `\documentclass[twocolumn]{article}` | Entire document in two-column layout |
| `figure*` / `table*` | Float spanning both columns (only `[t]`/`[b]` placement) |
| `\onecolumn` / `\twocolumn` | Switch column mode mid-document |
| `\begin{multicols}{n}...\end{multicols}` (needs `multicol`) | Multi-column block within a single-column document |

## Abstract
In LaTeX, the abstract is written inside an **`abstract` environment**:

```latex
\begin{abstract}
Your abstract text goes here...
\end{abstract}

```

---

### Where to put it?

Place it **inside the document body** (`\begin{document}`), **right after `\maketitle**` (and right before your first `\section`):

```latex
\documentclass[11pt]{article}

\title{Your Document Title}
\author{Your Name}
\date{\today}

\begin{document}

\maketitle

\begin{abstract}
This paper presents a concise summary of the study...
\end{abstract}

\section{Introduction}
Text starts here...

\end{document}

```

---

### Key Notes

* **Automatic Formatting:** The `abstract` environment automatically centers the word **"Abstract"** in bold, shrinks the font slightly, and adds indentation to set it apart from normal body text.
* **Document Class Compatibility:** The `abstract` environment works in standard classes like **`article`** and **`report`**. However, standard LaTeX **`book`** classes do not support `\begin{abstract}` by default (books typically use a preface instead).
## For boxes use fbox
`\fbox{}`