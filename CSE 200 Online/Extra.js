// This file for the browser extension "Snippetleaf" is in
// typescript and thus the import below is supported for the official extension "Snippetleaf",
// but may not be supported third-partys.
import {SnippetSignature, defineSnippets} from "./snippet_leaf";

// See https://github.com/superle3/snippet_leaf for more information about writing snippets.
/**
 * @typedef {Object} SnippetSignature
 * @property {string|RegExp} trigger
 * @property {string|((match: string | RegExpExecArray) => string|false)} replacement
 * @property {string} options t(ext), A(utomatic), w(ord-boundary), m(ath), M(display math), n(inline math)
 * @property {string} [flags] regex flags
 * @property {number} [priority] if two snippets can expand, the one with the higher priority will be used or if they have equal priority, the one that appears first in the list will be used.
 * @property {string} [description]
 * @property {number} [version] 1 | 2 = 2 Default can be changed in the settings and a higher version number may appear later down the line.
 */

const snippets : SnippetSignature[] = [
    // Math mode
    { trigger: "mk", replacement: "$@0$ @1", options: "tA" },
    { trigger: "dm", replacement: "\\[\n@0\n\\]", options: "tAw" },
    {
        trigger: "beg",
        replacement: "\\begin{@0}\n@1\n\\end{@0}",
        options: "mA",
    },
    { trigger: "test", replacement: "@0test @2", options: "mA" },

    // Dashes
    // {trigger: "--", replacement: "–", options: "tA"},
    // {trigger: "–-", replacement: "—", options: "tA"},
    // {trigger: "—-", replacement: "---", options: "tA"},

    // Greek letters
    { trigger: ";a", replacement: "\\alpha", options: "mA" },
    { trigger: ";b", replacement: "\\beta", options: "mA" },
    { trigger: ";g", replacement: "\\gamma", options: "mA" },
    { trigger: ";G", replacement: "\\Gamma", options: "mA" },
    { trigger: ";d", replacement: "\\delta", options: "mA" },
    { trigger: ";D", replacement: "\\Delta", options: "mA" },
    { trigger: ";e", replacement: "\\epsilon", options: "mA" },
    { trigger: ":e", replacement: "\\varepsilon", options: "mA" },
    { trigger: ";z", replacement: "\\zeta", options: "mA" },
    { trigger: ";t", replacement: "\\theta", options: "mA" },
    { trigger: ";T", replacement: "\\Theta", options: "mA" },
    { trigger: ":t", replacement: "\\vartheta", options: "mA" },
    { trigger: ";i", replacement: "\\iota", options: "mA" },
    { trigger: ";k", replacement: "\\kappa", options: "mA" },
    { trigger: ";l", replacement: "\\lambda", options: "mA" },
    { trigger: ";L", replacement: "\\Lambda", options: "mA" },
    { trigger: ";s", replacement: "\\sigma", options: "mA" },
    { trigger: ";S", replacement: "\\Sigma", options: "mA" },
    { trigger: ";u", replacement: "\\upsilon", options: "mA" },
    { trigger: ";U", replacement: "\\Upsilon", options: "mA" },
    { trigger: ";o", replacement: "\\omega", options: "mA" },
    { trigger: ";O", replacement: "\\Omega", options: "mA" },
    { trigger: "ome", replacement: "\\omega", options: "mA" },
    { trigger: "Ome", replacement: "\\Omega", options: "mA" },

    // Text environment
    { trigger: "text", replacement: "\\text{@0}@1", options: "mA" },
    { trigger: '"', replacement: "\\text{@0}@1", options: "mA" },

    // Basic operations
    { trigger: "sr", replacement: "^{2}", options: "mA" },
    { trigger: "cb", replacement: "^{3}", options: "mA" },
    { trigger: "rd", replacement: "^{@0}@1", options: "mA" },
    { trigger: "_", replacement: "_{@0}@1", options: "mA" },
    { trigger: "sts", replacement: "_\\text{@0}", options: "mA" },
    { trigger: "sq", replacement: "\\sqrt{ @0 }@1", options: "mA" },
    { trigger: "//", replacement: "\\frac{@0}{@1}@2", options: "mA" },
    { trigger: "ee", replacement: "e^{ @0 }@1", options: "mA" },
    { trigger: "invs", replacement: "^{-1}", options: "mA" },
    {
        trigger: /([A-Za-z])(\d)/,
        replacement: "@[0]_{@[1]}",
        options: "rmA",
        description: "Auto letter subscript",
        priority: -1,
    },

    {
        trigger: /([^\\])(exp|log|ln)/,
        replacement: "@[0]\\@[1]",
        options: "rmA",
    },
    { trigger: "conj", replacement: "^{*}", options: "mA" },
    { trigger: "Re", replacement: "\\mathrm{Re}", options: "mA" },
    { trigger: "Im", replacement: "\\mathrm{Im}", options: "mA" },
    { trigger: "bf", replacement: "\\mathbf{@0}", options: "mA" },
    { trigger: "rm", replacement: "\\mathrm{@0}@1", options: "mA" },

    // Linear algebra
    { trigger: /([^\\])(det)/, replacement: "@[0]\\@[1]", options: "rmA" },
    { trigger: "trace", replacement: "\\mathrm{Tr}", options: "mA" },

    // More operations
    { trigger: "([a-zA-Z])hat", replacement: "\\hat{@[0]}", options: "rmA" },
    { trigger: "([a-zA-Z])bar", replacement: "\\bar{@[0]}", options: "rmA" },
    {
        trigger: "([a-zA-Z])tilde",
        replacement: "\\tilde{@[0]}",
        options: "rmA",
    },
    {
        trigger: "([a-zA-Z])und",
        replacement: "\\underline{@[0]}",
        options: "rmA",
    },
    { trigger: "([a-zA-Z])vec", replacement: "\\vec{@[0]}", options: "rmA" },
    {
        trigger: "([a-zA-Z]),\\.",
        replacement: "\\mathbf{@[0]}",
        options: "rmA",
    },
    {
        trigger: "([a-zA-Z])\\.,",
        replacement: "\\mathbf{@[0]}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}),\\.",
        replacement: "\\boldsymbol{\\@[0]}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK})\\.,",
        replacement: "\\boldsymbol{\\@[0]}",
        options: "rmA",
    },

    { trigger: "hat", replacement: "\\hat{@0}@1", options: "mA" },
    { trigger: "bar", replacement: "\\bar{@0}@1", options: "mA" },
    { trigger: "ddot", replacement: "\\ddot{@0}@1", options: "mA" },
    { trigger: "tilde", replacement: "\\tilde{@0}@1", options: "mA" },
    { trigger: "und", replacement: "\\underline{@0}@1", options: "mA" },
    { trigger: "vec", replacement: "\\vec{@0}@1", options: "mA" },

    // More auto letter subscript
    {
        trigger: /([A-Za-z])_(\d\d)/,
        replacement: "@[0]_{@[1]}",
        options: "rmA",
    },
    {
        trigger: /\\hat{([A-Za-z])}(\d)/,
        replacement: "\\hat{@[0]}_{@[1]}",
        options: "rmA",
    },
    {
        trigger: /\\vec{([A-Za-z])}(\d)/,
        replacement: "\\vec{@[0]}_{@[1]}",
        options: "rmA",
    },
    {
        trigger: /\\mathbf{([A-Za-z])}(\d)/,
        replacement: "\\mathbf{@[0]}_{@[1]}",
        options: "rmA",
    },

    { trigger: "xnn", replacement: "x_{n}", options: "mA" },
    { trigger: "\\xii", replacement: "x_{i}", options: "mA", priority: 1 },
    { trigger: "xjj", replacement: "x_{j}", options: "mA" },
    { trigger: "xp1", replacement: "x_{n+1}", options: "mA" },
    { trigger: "ynn", replacement: "y_{n}", options: "mA" },
    { trigger: "yii", replacement: "y_{i}", options: "mA" },
    { trigger: "yjj", replacement: "y_{j}", options: "mA" },

    // Symbols
    { trigger: "ooo", replacement: "\\infty", options: "mA" },
    {
        trigger: "\\sum",
        replacement: "\\sum_{@{0:i}=@{1:1}}^{@{2:N}} @3",
        options: "m",
    },
    {
        trigger: "\\prod",
        replacement: "\\prod_{@{0:i}=@{1:1}}^{@{2:N}} @3",
        options: "m",
    },
    {
        trigger: "lim",
        replacement: "\\lim_{ @{0:n} \\to @{1:\\infty} } @2",
        options: "mA",
    },
    { trigger: "+-", replacement: "\\pm", options: "mA" },
    { trigger: "-+", replacement: "\\mp", options: "mA" },
    { trigger: "...", replacement: "\\dots", options: "mA" },
    { trigger: "nabl", replacement: "\\nabla", options: "mA" },
    { trigger: "del", replacement: "\\nabla", options: "mA" },
    { trigger: "xx", replacement: "\\times", options: "mA" },
    { trigger: "**", replacement: "\\cdot", options: "mA" },
    { trigger: "para", replacement: "\\parallel", options: "mA" },

    { trigger: "===", replacement: "\\equiv", options: "mA" },
    { trigger: "!=", replacement: "\\neq", options: "mA" },
    { trigger: ">=", replacement: "\\geq", options: "mA" },
    { trigger: "<=", replacement: "\\leq", options: "mA" },
    { trigger: ">>", replacement: "\\gg", options: "mA" },
    { trigger: "<<", replacement: "\\ll", options: "mA" },
    { trigger: "simm", replacement: "\\sim", options: "mA" },
    { trigger: "sim=", replacement: "\\simeq", options: "mA" },
    { trigger: "prop", replacement: "\\propto", options: "mA" },

    { trigger: "<->", replacement: "\\leftrightarrow ", options: "mA" },
    { trigger: "->", replacement: "\\to", options: "mA" },
    { trigger: "!>", replacement: "\\mapsto", options: "mA" },
    { trigger: "=>", replacement: "\\implies", options: "mA" },
    { trigger: "=<", replacement: "\\impliedby", options: "mA" },

    { trigger: "and", replacement: "\\cap", options: "mA" },
    { trigger: "orr", replacement: "\\cup", options: "mA" },
    { trigger: "inn", replacement: "\\in", options: "mA" },
    { trigger: "notin", replacement: "\\not\\in", options: "mA" },
    { trigger: "\\\\\\", replacement: "\\setminus", options: "mA" },
    { trigger: "sub=", replacement: "\\subseteq", options: "mA" },
    { trigger: "sup=", replacement: "\\supseteq", options: "mA" },
    { trigger: "eset", replacement: "\\emptyset", options: "mA" },
    { trigger: "set", replacement: "\\{ @0 \\}@1", options: "mA" },
    {
        trigger: "e\\xi sts",
        replacement: "\\exists",
        options: "mA",
        priority: 1,
    },

    { trigger: "LL", replacement: "\\mathcal{L}", options: "mA" },
    { trigger: "HH", replacement: "\\mathcal{H}", options: "mA" },
    { trigger: "CC", replacement: "\\mathbb{C}", options: "mA" },
    { trigger: "RR", replacement: "\\mathbb{R}", options: "mA" },
    { trigger: "ZZ", replacement: "\\mathbb{Z}", options: "mA" },
    { trigger: "NN", replacement: "\\mathbb{N}", options: "mA" },

    // Handle spaces and backslashes

    // Snippet variables can be used as shortcuts when writing snippets.
    // For example, ${GREEK} below is shorthand for "alpha|beta|gamma|Gamma|delta|..."
    // You can edit snippet variables under the Advanced snippet settings section.

    {
        trigger: "([^\\\\])(${GREEK})",
        replacement: "@[0]\\@[1]",
        options: "rmA",
        description: "Add backslash before Greek letters",
    },
    {
        trigger: "([^\\\\])(${SYMBOL})",
        replacement: "@[0]\\@[1]",
        options: "rmA",
        description: "Add backslash before symbols",
    },

    // Insert space after Greek letters and symbols
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}|${MORE_SYMBOLS})([A-Za-z])",
        replacement: "\\@[0] @[1]",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) sr",
        replacement: "\\@[0]^{2}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) cb",
        replacement: "\\@[0]^{3}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) rd",
        replacement: "\\@[0]^{@0}@1",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) hat",
        replacement: "\\hat{\\@[0]}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) bar",
        replacement: "\\bar{\\@[0]}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) vec",
        replacement: "\\vec{\\@[0]}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) tilde",
        replacement: "\\tilde{\\@[0]}",
        options: "rmA",
    },
    {
        trigger: "\\\\(${GREEK}|${SYMBOL}) und",
        replacement: "\\underline{\\@[0]}",
        options: "rmA",
    },

    // Derivatives and integrals
    {
        trigger: "par",
        replacement: "\\frac{ \\partial @{0:y} }{ \\partial @{1:x} } @2",
        options: "m",
    },
    {
        trigger: /pa([A-Za-z])([A-Za-z])/,
        replacement: "\\frac{ \\partial @[0] }{ \\partial @[1] } ",
        options: "rm",
    },
    { trigger: "ddt", replacement: "\\frac{d}{dt} ", options: "mA" },

    {
        trigger: /([^\\])int/,
        replacement: "@[0]\\int",
        options: "mA",
        priority: -1,
    },
    { trigger: "\\int", replacement: "\\int @0 \\, d@{1:x} @2", options: "m" },
    {
        trigger: "dint",
        replacement: "\\int_{@{0:0}}^{@{1:1}} @2 \\, d@{3:x} @4",
        options: "mA",
    },
    { trigger: "oint", replacement: "\\oint", options: "mA" },
    { trigger: "iint", replacement: "\\iint", options: "mA" },
    { trigger: "iiint", replacement: "\\iiint", options: "mA" },
    {
        trigger: "oinf",
        replacement: "\\int_{0}^{\\infty} @0 \\, d@{1:x} @2",
        options: "mA",
    },
    {
        trigger: "infi",
        replacement: "\\int_{-\\infty}^{\\infty} @0 \\, d@{1:x} @2",
        options: "mA",
    },

    // Trigonometry
    {
        trigger: /([^\\])(arcsin|sin|arccos|cos|arctan|tan|csc|sec|cot)/,
        replacement: "@[0]\\@[1]",
        options: "rmA",
        description: "Add backslash before trig funcs",
    },

    {
        trigger:
            /\\(arcsin|sin|arccos|cos|arctan|tan|csc|sec|cot)([A-Za-gi-z])/,
        replacement: "\\@[0] @[1]",
        options: "rmA",
        description:
            "Add space after trig funcs. Skips letter h to allow sinh, cosh, etc.",
    },

    {
        trigger: /\\(sinh|cosh|tanh|coth)([A-Za-z])/,
        replacement: "\\@[0] @[1]",
        options: "rmA",
        description: "Add space after hyperbolic trig funcs",
    },

    // Visual operations
    {
        trigger: "U",
        replacement: "\\underbrace{ ${VISUAL} }_{ $0 }",
        options: "mA",
        version: 1,
    },
    {
        trigger: "O",
        replacement: "\\overbrace{ @{VISUAL} }^{ @0 }",
        options: "mA",
    },
    {
        trigger: "B",
        replacement: "\\underset{ @0 }{ @{VISUAL} }",
        options: "mA",
    },
    { trigger: "C", replacement: "\\cancel{ @{VISUAL} }", options: "mA" },
    {
        trigger: "K",
        replacement: "\\cancelto{ @0 }{ @{VISUAL} }",
        options: "mA",
    },
    { trigger: "S", replacement: "\\sqrt{ @{VISUAL} }", options: "mA" },

    // Physics
    { trigger: "kbt", replacement: "k_{B}T", options: "mA" },
    { trigger: "msun", replacement: "M_{\\odot}", options: "mA" },

    // Quantum mechanics
    { trigger: "dag", replacement: "^{\\dagger}", options: "mA" },
    { trigger: "o+", replacement: "\\oplus ", options: "mA" },
    { trigger: "ox", replacement: "\\otimes ", options: "mA" },
    { trigger: "bra", replacement: "\\bra{@0} @1", options: "mA" },
    { trigger: "ket", replacement: "\\ket{@0} @1", options: "mA" },
    { trigger: "brk", replacement: "\\braket{ @0 | @1 } @2", options: "mA" },
    {
        trigger: "outer",
        replacement: "\\ket{@{0:\\psi}} \\bra{@{0:\\psi}} @1",
        options: "mA",
    },

    // Chemistry
    { trigger: "pu", replacement: "\\pu{ @0 }", options: "mA" },
    { trigger: "cee", replacement: "\\ce{ @0 }", options: "mA" },
    { trigger: "he4", replacement: "{}^{4}_{2}He ", options: "mA" },
    { trigger: "he3", replacement: "{}^{3}_{2}He ", options: "mA" },
    {
        trigger: "iso",
        replacement: "{}^{@{0:4}}_{@{1:2}}@{2:He}",
        options: "mA",
    },

    // Environments
    {
        trigger: "pmat",
        replacement: "\\begin{pmatrix}\n@0\n\\end{pmatrix}",
        options: "MA",
    },
    {
        trigger: "bmat",
        replacement: "\\begin{bmatrix}\n@0\n\\end{bmatrix}",
        options: "MA",
    },
    {
        trigger: "Bmat",
        replacement: "\\begin{Bmatrix}\n@0\n\\end{Bmatrix}",
        options: "MA",
    },
    {
        trigger: "vmat",
        replacement: "\\begin{vmatrix}\n@0\n\\end{vmatrix}",
        options: "MA",
    },
    {
        trigger: "Vmat",
        replacement: "\\begin{Vmatrix}\n@0\n\\end{Vmatrix}",
        options: "MA",
    },
    {
        trigger: "matrix",
        replacement: "\\begin{matrix}\n@0\n\\end{matrix}",
        options: "MA",
    },

    {
        trigger: "pmat",
        replacement: "\\begin{pmatrix}@0\\end{pmatrix}",
        options: "nA",
    },
    {
        trigger: "bmat",
        replacement: "\\begin{bmatrix}@0\\end{bmatrix}",
        options: "nA",
    },
    {
        trigger: "Bmat",
        replacement: "\\begin{Bmatrix}@0\\end{Bmatrix}",
        options: "nA",
    },
    {
        trigger: "vmat",
        replacement: "\\begin{vmatrix}@0\\end{vmatrix}",
        options: "nA",
    },
    {
        trigger: "Vmat",
        replacement: "\\begin{Vmatrix}@0\\end{Vmatrix}",
        options: "nA",
    },
    {
        trigger: "matrix",
        replacement: "\\begin{matrix}@0\\end{matrix}",
        options: "nA",
    },

    {
        trigger: "cases",
        replacement: "\\begin{cases}\n@0\n\\end{cases}",
        options: "mA",
    },
    {
        trigger: "align",
        replacement: "\\begin{align}\n@0\n\\end{align}",
        options: "mA",
    },
    {
        trigger: "array",
        replacement: "\\begin{array}\n@0\n\\end{array}",
        options: "mA",
    },

    // Brackets
    { trigger: "avg", replacement: "\\langle @0 \\rangle @1", options: "mA" },
    {
        trigger: "norm",
        replacement: "\\lvert @0 \\rvert @1",
        options: "mA",
        priority: 1,
    },
    {
        trigger: "Norm",
        replacement: "\\lVert @0 \\rVert @1",
        options: "mA",
        priority: 1,
    },
    { trigger: "ceil", replacement: "\\lceil @0 \\rceil @1", options: "mA" },
    { trigger: "floor", replacement: "\\lfloor @0 \\rfloor @1", options: "mA" },
    { trigger: "mod", replacement: "|@0|@1", options: "mA" },
    { trigger: "(", replacement: "(@{VISUAL})", options: "mA" },
    { trigger: "[", replacement: "[@{VISUAL}]", options: "mA" },
    { trigger: "{", replacement: "{@{VISUAL}}", options: "mA" },
    { trigger: "(", replacement: "(@0)@1", options: "mA" },
    { trigger: "{", replacement: "{@0}@1", options: "mA" },
    { trigger: "[", replacement: "[@0]@1", options: "mA" },
    { trigger: "lr(", replacement: "\\left( @0 \\right) @1", options: "mA" },
    {
        trigger: "lr{",
        replacement: "\\left\\{ @0 \\right\\} @1",
        options: "mA",
    },
    { trigger: "lr[", replacement: "\\left[ @0 \\right] @1", options: "mA" },
    { trigger: "lr|", replacement: "\\left| @0 \\right| @1", options: "mA" },
    { trigger: "lra", replacement: "\\left< @0 \\right> @1", options: "mA" },

    // Misc

    // Automatically convert standalone letters in text to math (except a, A, I).
    // (Un-comment to enable)
    // {trigger: /([^'])\b([B-HJ-Zb-z])\b([\n\s.,?!:'])/, replacement: "@[0]$@[1]$@[2]", options: "tA"},

    // Automatically convert Greek letters in text to math.
    // {trigger: "(${GREEK})([\\n\\s.,?!:'])", replacement: "$\\@[0]$@[1]", options: "rtAw"},

    // Automatically convert text of the form "x=2" and "x=n+1" to math.
    // {trigger: /([A-Za-z]=\d+)([\n\s.,?!:'])/, replacement: "$@[0]$@[1]", options: "rtAw"},
    // {trigger: /([A-Za-z]=[A-Za-z][+-]\d+)([\n\s.,?!:'])/, replacement: "$@[0]$@[1]", options: "tAw"},

    // Snippet replacements can have placeholders.
    {
        trigger: "tayl",
        replacement:
            "@{0:f}(@{1:x} + @{2:h}) = @{0:f}(@{1:x}) + @{0:f}'(@{1:x})@{2:h} + @{0:f}''(@{1:x}) \\frac{@{2:h}^{2}}{2!} + \\dots@3",
        options: "mA",
        description: "Taylor expansion",
    },

    // Snippet replacements can also be JavaScript functions.
    // See the documentation for more information.
    {
        trigger: /iden(\d)/,
        replacement: (match) => {
            const n = match[1];

            let arr = [];
            for (let j = 0; j < n; j++) {
                arr[j] = [];
                for (let i = 0; i < n; i++) {
                    arr[j][i] = i === j ? 1 : 0;
                }
            }

            let output = arr.map((el) => el.join(" & ")).join(" \\\\\n");
            output = `\\begin{pmatrix}\n${output}\n\\end{pmatrix}`;
            return output;
        },
        options: "mA",
        description: "N x N identity matrix",
    },
    // My snippets
    {
        trigger: "doc;",
        options: "tAw",
        replacement: `\\documentclass[11pt,a4paper]{article}
    
\\usepackage[utf8]{inputenc}
\\usepackage[T1]{fontenc}
\\usepackage{lmodern}
\\usepackage{microtype}
    
\\usepackage{amsmath, amssymb, amsthm}
    
\\usepackage{graphicx}
\\usepackage{wrapfig}
\\usepackage{multirow}
\\usepackage{booktabs}
    
\\usepackage[table, dvipsnames, svgnames, HTML]{xcolor}
\\usepackage{listings}
\\usepackage{multicol}
\\usepackage{enumitem}
\\usepackage{float}
\\usepackage{algorithm}
\\usepackage{algpseudocode}
\\usepackage[numbers]{natbib}
\\usepackage{subcaption}
    
\\usepackage[a4paper,
    top=2.0cm,
    bottom=2.0cm,
    left=2.25cm,
    right=2.25cm]{geometry}
\\usepackage{hyperref}
\\usepackage{doi}
\\providecommand{\\algorithmautorefname}{Algorithm}
\\graphicspath{ {./} }
\\setlength{\\parindent}{0pt}
\\setlength{\\parskip}{0.55em}
\\newtheorem{theorem}{Theorem}
\\definecolor{customblue}{RGB}{0, 76, 153}
\\hypersetup{
colorlinks=true,
linkcolor=customblue,
citecolor=customblue,
urlcolor=teal
}
\\lstset{
    basicstyle=\\ttfamily\\small,
    keywordstyle=\\color{customblue}\\bfseries,
    commentstyle=\\color{teal!70!black},
    stringstyle=\\color{red!70!black},
    numbers=left,
    numberstyle=\\tiny\\color{gray},
    frame=single,
    breaklines=true,
    showstringspaces=false,
    columns=fullflexible
}
    
    
\\title{\\vspace{-1.5cm} @0}
\\author{@1}
\\date{@2}
    
    
\\begin{document}
    
\\maketitle
\\vspace{-1.1cm}
    
    
@3
    
\\nocite{*}
\\bibliographystyle{plainnat}
\\bibliography{} 
\\end{document}`
},
// 1. For normal typing (from scratch)
    { 
        trigger: "bb", 
        replacement: "\\textbf{@0}@1", 
        options: "tA",
        description: "Bold text"
    },
    
  { 
        trigger: "b", 
        replacement: "\\textbf{@{VISUAL}}@0",
        options: "tv",
        description: "Visual Bold"
    },
    // No indent
    { 
        trigger: "noin", 
        replacement: "\\noindent @0", 
        options: "tAw",
        description: "No indent with newline"
    },
    // Remove or rename Table of Contents title
    { 
        trigger: "retoc", 
        replacement: "\\renewcommand*\\contentsname{@0}@1", 
        options: "tAw",
        description: "Rename or clear Table of Contents name"
    },
    // Text color
    { 
        trigger: "c;", 
        replacement: "\\textcolor{@0}{@1}@2", 
        options: "tAw",
        description: "Text color"
    },
    // Dynamic single-variable derivative (e.g., ddx -> \frac{d}{dx}, ddt -> \frac{d}{dt})
    // Dynamic single-variable derivative (e.g., ddx -> \frac{d}{dx})
    { 
        trigger: /dd([a-zA-Z])/, 
        replacement: "\\frac{d{@0}}{d@[0]} @1", 
        options: "rmA",
        description: "Dynamic derivative with numerator cursor first"
    },
    // Numbered Equation Environment (Trigger: eq;)
    {
        trigger: "eq;",
        replacement: "\\begin{equation}\n\t@0\n\\end{equation}@1",
        options: "tAw",
        description: "Numbered equation environment"
    },
    // Ordered List with explicit label option
    {
        trigger: "ol;",
        replacement: "\\begin{enumerate}[label=@0]\n\t\\item @1\n\\end{enumerate}@2",
        options: "tAw",
        description: "Ordered list with explicit label option"
    },

    // Unordered List with explicit label option
    {
        trigger: "ul;",
        replacement: "\\begin{itemize}[label=@0]\n\t\\item @1\n\\end{itemize}@2",
        options: "tAw",
        description: "Unordered list with explicit label option"
    },
    // Multirow: mr2; -> \multirow{2}{*}{@0}
    {
        trigger: /mr(\d+);/,
        replacement: (match) => `\\multirow{${match[1]}}{*}{@0}@1`,
        options: "tA",
        description: "Dynamic multirow"
    },

    // Multicolumn: mc2; -> \multicolumn{2}{|c|}{@0}
    {
        trigger: /mc(\d+);/,
        replacement: (match) => `\\multicolumn{${match[1]}}{|c|}{@0}@1`,
        options: "tA",
        description: "Dynamic multicolumn"
    },
    // Newline with hline
    {
        trigger: "nl;",
        replacement: "\\\\\n\\hline\n@0",
        options: "tA",
        description: "Newline with hline"
    },
    // Dynamic cline: c12; -> \\ \cline{1-2}
    {
        trigger: /c(\d)(\d);/,
        replacement: (match) => `\\cline{${match[1]}-${match[2]}}\n@0`,
        options: "tA",
        description: "Dynamic cline with two columns"
    },
    // Figure environment with caption and label
    {
        trigger: "fig;",
        replacement: "\\begin{figure}[H]\n\t\\centering\n\t\\includegraphics[width=\\linewidth, scale=1, angle=0]{@0}\n\t\\caption{@1}\n\t\\label{fig:@2}\n\\end{figure}@3",
        options: "tAw",
        description: "Figure environment"
    },
    // Wrapfigure environment template
    {
        trigger: "wfig;",
        replacement: "\\begin{wrapfigure}{r}{0.4\\linewidth}\n\t\\centering\n\t\\includegraphics[width=\\linewidth]{@0}\n\t\\caption{@1}\n\t\\label{fig:@2}\n\\end{wrapfigure}@3",
        options: "tAw",
        description: "Wrapfigure environment"
    },
    // Algorithm environment with Require and Ensure
    {
        trigger: "algo;",
        replacement: "\\begin{algorithm}[H]\n\t\\caption{@0}\n\t\\label{alg:@1}\n\t\\begin{algorithmic}[1]\n\t\t\\Require @2\n\t\t\\Ensure @3\n\t\t@4\n\t\\end{algorithmic}\n\\end{algorithm}@5",
        options: "tAw",
        description: "Algorithm environment with Require and Ensure"
    },
    // Pseudocode State: st; -> \State @0
    {
        trigger: "st;",
        replacement: "\\State @0",
        options: "tAw",
        description: "Algorithmic state"
    },

    // Return State: ret; -> \State \Return @0
    {
        trigger: "return;",
        replacement: "\\State \\Return @0",
        options: "tAw",
        description: "Algorithmic return statement"
    },

    // If Block: if; -> \If{@0}\n\t@1\n\EndIf@2
    {
        trigger: "if;",
        replacement: "\\If{@0}\n\t@1\n\\EndIf@2",
        options: "tAw",
        description: "Algorithmic if block"
    },

    // ElsIf Branch: elif; -> \ElsIf{@0}\n\t@1
    {
        trigger: "elif;",
        replacement: "\\ElsIf{@0}\n\t@1",
        options: "tAw",
        description: "Algorithmic elsif branch"
    },

    // Else Branch: else; -> \Else\n\t@0
    {
        trigger: "else;",
        replacement: "\\Else\n\t@0",
        options: "tAw",
        description: "Algorithmic else branch"
    },

    // While Loop: wh; -> \While{@0}\n\t@1\n\EndWhile@2
    {
        trigger: "while;",
        replacement: "\\While{@0}\n\t@1\n\\EndWhile@2",
        options: "tAw",
        description: "Algorithmic while loop"
    },

    // For Loop: for; -> \For{@0 $\gets$ @1 to @2}\n\t@3\n\EndFor@4
    {
        trigger: "for;",
        replacement: "\\For{$@0\\gets$  to @2}\n\t@3\n\\EndFor@4",
        options: "tAw",
        description: "Algorithmic for loop"
    },

    // ForAll Loop: fall; -> \ForAll{@0 \in @1}\n\t@2\n\EndFor@3
    {
        trigger: "fall;",
        replacement: "\\ForAll{@0 \\in @1}\n\t@2\n\\EndFor@3",
        options: "tAw",
        description: "Algorithmic forall loop"
    },
    // Display style
    {
        trigger: "ds",
        replacement: "\\displaystyle ",
        options: "mA",
        description: "Display style in math mode"
    },
    // Auto-ref to autoref: ref -> \autoref{@0}
    {
        trigger: "ref",
        replacement: "\\autoref{@0}@1",
        options: "tAw",
        description: "Automatic autoref"
    },
    // Listing code environment: code;
    {
        trigger: "code;",
        replacement: "\\begin{lstlisting}[language=@0, caption={@1}, label={lst:@2}, captionpos=b]\n@3\n\\end{lstlisting}@4",
        options: "tAw",
        description: "Python/Code listing environment"
    },
    // Dynamic subfigures: 2subfig; or 3subfig; -> N indented subfigures with \hfill
    {
        trigger: /(\d+)subfig;/,
        replacement: (match) => {
            const count = parseInt(match[1]);
            let subfigs = [];
            let tabstop = 0;

            for (let i = 0; i < count; i++) {
                subfigs.push(
                    `\t\\begin{subfigure}{0.45\\linewidth}\n` +
                    `\t\t\\centering\n` +
                    `\t\t\\includegraphics[width=\\linewidth]{@${tabstop++}}\n` +
                    `\t\t\\caption{@${tabstop++}}\n` +
                    `\t\t\\label{fig:@${tabstop++}}\n` +
                    `\t\\end{subfigure}`
                );
            }

            // Join subfigures with \hfill between them
            const body = subfigs.join("\n\t\\hfill\n");

            return (
                `\\begin{figure}[H]\n` +
                `\t\\centering\n` +
                `${body}\n` +
                `\t\\caption{@${tabstop++}}\n` +
                `\t\\label{fig:@${tabstop++}}\n` +
                `\\end{figure}@${tabstop}`
            );
        },
        options: "tA",
        description: "Dynamic N subfigures with hfill and proper indentation"
    },
    // Table of contents
    {
        trigger: "toc",
        replacement: "\\tableofcontents\n@0",
        options: "tAw",
        description: "Table of contents"
    },
    // Summation with limits: sum -> \sum_{@0}^{@1} @2
    {
        trigger: "sum",
        replacement: "\\sum_{@0}^{@1} @2",
        options: "mA",
        description: "Summation with limits"
    },

    // Product with limits: prod -> \prod_{@0}^{@1} @2
    {
        trigger: "prod",
        replacement: "\\prod_{@0}^{@1} @2",
        options: "mA",
        description: "Product with limits"
    },
    // Infinity: inf -> \infty
    {
        trigger: "inf",
        replacement: "\\infty",
        options: "mA",
        description: "Infinity symbol"
    },
    // Nonumber: non -> \nonumber
    {
        trigger: "non",
        replacement: "\\nonumber",
        options: "mA",
        description: "No number tag in math mode"
    },
    // Double quad space: qq -> \qquad
    {
        trigger: "qq",
        replacement: "\\qquad ",
        options: "mA",
        priority: 10,
        description: "Double quad space in math mode"
    },
    // Single quad space: qd -> \quad
    {
        trigger: "qd",
        replacement: "\\quad ",
        options: "mA",
        priority: 10,
        description: "Quad space in math mode"
    },
    // Dynamic Table: tab10x10; -> 10 rows, 10 columns table
    {
        trigger: /tab(\d+)x(\d+);/,
        replacement: (match) => {
            const rows = parseInt(match[1]);
            const cols = parseInt(match[2]);

            // Create column specifiers: |c|c|c|
            const colSpec = "|" + Array(cols).fill("c").join("|") + "|";

            let tabstop = 0;
            let rowLines = [];

            for (let r = 0; r < rows; r++) {
                let cellItems = [];
                for (let c = 0; c < cols; c++) {
                    cellItems.push(`@${tabstop++}`);
                }
                // Join cells with ' & ' and end with '\\\n\n' (1 empty line after \\)
                rowLines.push(`\t\t${cellItems.join(" & ")} \\\\\n`);
            }

            const body = rowLines.join("\n");

            return (
                `\\begin{table}[H]\n` +
                `\t\\centering\n` +
                `\t\\begin{tabular}{${colSpec}}\n` +
                `${body}` +
                `\t\\end{tabular}\n` +
                `\t\\caption{@${tabstop++}}\n` +
                `\t\\label{tab:@${tabstop++}}\n` +
                `\\end{table}@${tabstop}`
            );
        },
        options: "tA",
        description: "Dynamic R x C table with |c| columns, no hline, and empty lines between rows"
    },
{
    trigger: /([A-Za-z]|\\[A-Za-z]+)\.([A-Za-z])/,
    replacement: "@[0]_@[1]",
    options: "rmA",
    description: "Simple dot subscript"
},
    // Item snippet: i; -> \item
    {
        trigger: "i;",
        replacement: "\\item @0",
        options: "tAw",
        description: "List item"
    },
];

export default snippets;
