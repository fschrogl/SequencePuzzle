# SequencePuzzle

Find different ways of compressing char sequences. The `compress` function takes a string
as input and returns this string as a compressed version.

Examples of inputs and matching (i.e. successfully compressed) output strings:

Input | Output
--- | ---
aaabbccccdeee | a3b2c4d1e3
aabbaaaffgghhxx | a2b2a3f2g2h2x2

Some things that can be taken as a given:

* An input string will only consist of lower case letters
* Same letters can be used multiple times throughout the input string
* Input string will only contain latin characters `a-z`
