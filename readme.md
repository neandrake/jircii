jIRCii 
=====
Personal fork of Java IRC Client [website](http://www.oldschoolirc.com/) / [source](https://code.google.com/p/jircii/)

- `Maintenance`: Large-scale architecture cleanup
	- Remove excessive singleton pattern favoring application hierarchy
	- Separate UI from IRC engine
	- Update Collection to use generics
- `Fix`: Multi-line text selection/copy in channel copies additional text after selection end
- `Feature`: SSL Keystore for servers
- `Feature`: ...I kinda want to add Lua/Python as a scripting engine