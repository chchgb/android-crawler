This document lists the entries to set in the XML Preferences file in order to set up the parameters of the ripping session in the start-up phase.



# General parameters #

Following are the entries of the map stored in the main node of the XML file.

**PACKAGE\_NAME**: `String`

The name of the package that contains the classes (specifically, the activities) which make up the application under test.

Technically, this parameter defines the target package for the Instrumentation; as a result, any attempt to exercise items of software residing outside this package will result in a Runtime Exception and a "dead" branch of the Gui Tree.

**CLASS\_NAME**: `String`

The fully qualified name (i.e. including the package) of the class of the start activity of the application under test.

Every trace in the ripping session will have this activity as its starting point.

**RANDOM\_SEED**: `long` {0 = random}

This value is used to setup the randon number generation mechanism. Several instances of this are used with various purposes such as: filling in the values of text fields, randomizing the sequence of actions to be performed when using the Random engine, and so on...

When initialized with the same seed, the Ripper should be able to generate the same sequence of random numbers, allowing the exact repetition of a session.

When set to zero, a random seed will be generated at startup based on the system time.

# Automation parameters #

Following are the entries of the map stored in the `automation` sub-node (direct child of the main node) of the XML file.

**SLEEP\_AFTER\_EVENT**: `int`

How many milliseconds to wait after perfoming each of the actions of which a task is comprised of.

**SLEEP\_AFTER\_RESTART**: `int`

How many milliseconds to wait at restart, that is before perfoming any of the actions in a task.

**SLEEP\_AFTER\_TASK**: `int`

How many (additional) milliseconds to wait at the end of a task, after having perfomed all the actions it is comprised of.

**SLEEP\_ON\_THROBBER**: `int`

How many milliseconds to wait after an event if a throbber (spinning wheel displayed when the user is supposed to wait) is on the screen.

Note that the Ripper will always wait **SLEEP\_AFTER\_EVENT** ms even if the throbber disappears before this amount of time. Then, it waits for the throbber to disappear; if **SLEEP\_ON\_THROBBER** ms pass, the Ripper proceeds to the next event, even if the throbber is still displayed.

**FORCE\_RESTART**: `boolean`

When `true`, an Intent is sent to the start activity of the application under test before processing a task, ensuring that all the traces begin from the same starting point.

This is only useful for debugging purposes, and/or when using the Ripper as a stand-alone tool. The testing harness provided to excercise Android applications already makes sure that all traces start, not just from the same activity, but from the exact same state of the device.

**IN\_AND\_OUT\_FOCUS**: `boolean`

When `false`, elements of the GUI that fall outside the boundary of the screen are ignored.

This is a legacy parameter and is usually better left to `true`.

**PRECRAWLING**: `String []`

A string array containing the precrawling sequence.
The Separator (placeholder for the null object, not to be confused with the `null` String) is ".-.-.";

Refer to [here](https://code.google.com/p/android-crawler/source/browse/AndroidCrawler/src/com/nofatclips/crawler/Resources.java?spec=svn189&r=189)

**SCREENSHOT\_FOR\_STATES**: `boolean`

When `true`, an image capture of the display screen is performed after the last action in a task has been performed and saved as a JPEG file.

**SCREENSHOT\_ONLY\_NEW\_STATES**: `boolean`

When `true`, a screenshot is saved at the end of a task only when a new state is found; in other words, when the final state of a task is equivalent to one of the states already discovered (which we already have a screenshot of), no further pictures are taken.

Ignored if **SCREENSHOT\_FOR\_STATES** = `false`

# Scheduler parameters #

Following are the entries of the map stored in the `scheduler` sub-node of the XML file.

**SCHEDULER\_ALGORITHM**: {BREADTH\_FIRST|DEPTH\_FIRST}

The strategy used to explore the application under test:
  * with the `DEPTH_FIRST` algorithm, the branch starting from a node of the GUI Tree is explored thoroughly before exploring the neighbours of (nodes at the same depth than) that node;
  * with the `BREADTH_FIRST` algorithm, on the opposite, all neighbouring nodes are explored before descending to deeper nodes.

Note that the `Random ripper` forces the use of the `depth first` strategy.

**MAX\_TASKS\_IN\_SCHEDULER**: `int`

Tasks to be performed during the ripping session are planned in advance and stored in a task list. This parameter limits the dimension of this list.

This is useful when using the Random ripper, since it can generate very long sequence of actions, leading to long I/O operations and possibly "Out of Memory" errors.

# Interactions parameters #

Following are the entries of the map stored in the `interactions` sub-node of the XML file.

**EVENTS**: `String []`

Defines the type of the interactions to be fired by the Ripper as events, and the types of the widget upon which to fire them.

Each `String` in the array will start with the Interaction Type of the event to fire, followed by the Simple Types of the widgets on which the event should be fired, separated by a comma. Spaces will be ignored.

Example: `"click, button, image, menuItem"`

**INPUTS**: `String []`

Defines the type of the interactions to be fired by the Ripper as inputs and the types of the widget upon which to fire them.

The format is the same as for the **EVENTS**.

**KEY\_EVENTS**: `int []`

Defines which hardware keys of the device will be pressed by the Ripper. Each key press will be an event.

The allowed values are the key code constants listed in the documentation for the [KeyEvent](http://developer.android.com/reference/android/view/KeyEvent.html) class.

**BACK\_BUTTON\_EVENT**: `boolean`

Short hand to perform the back key press.

**MENU\_EVENTS**: `boolean`

Short hand to perform the menu key press.

**ORIENTATION\_EVENTS**: `boolean`

When `true`, a special event will be fired to change the orientation of the device from "portrait" to "landscape".

**SCROLL\_DOWN\_EVENT**: `boolean`

When `true`, a special event will be fired to scroll the display one page down.

**ACTIONBARHOME\_EVENTS**: `boolean`

Clicks an `ActionBar` Home/Up button.

# Planner parameters #

Following are the entries of the map stored in the `planner` sub-node of the XML file.

**PLANNER**: `String`

The possible values are:

`SimplePlanner` {the default value}

`DictionarySimplePlanner`

**TEXT\_VALUES\_ID\_HASH**: `boolean`

When `true`, uses the hash value of the id to fill the Edit Text

Ignored if **PLANNER** = `DictionarySimplePlanner`

**TEXT\_VALUES\_FROM\_DICTIONARY**: `boolean`

When `true`, uses the values in dictionaries to fill the Edit Text {default = `false`}.

**DICTIONARY\_IGNORE\_CONTENT\_TYPES**: `boolean`

When `true`, takes a random value from the dictionary of any contentType {default is `false`}.

**VALID\_DICTIONARY\_VALUES**: `boolean` <not yet implemented>

**INVALID\_DICTIONARY\_VALUES**: `boolean` <not yet implemented>

**MAX\_EVENTS\_PER\_WIDGET**: `int` {0 = `infinite`}

Limits the number of times an event is generated when iterating on a group widget.

For instance, when **MAX\_EVENTS\_PER\_WIDGET** = `0` the `selectListItem` interaction generates a select event (scroll, focus and click) for each entry in a List View.

If **MAX\_EVENTS\_PER\_WIDGET** = `5`, only the first five elements in the List View (including separators) will be selected.

**ALL\_EVENTS\_ON\_PREFERENCES**: `boolean`

When `true`, the value of **MAX\_EVENTS\_PER\_WIDGET** is ignored for Preferences lists; that is, all entries in the Preferences will be selected even when a limit is set for ordinary lists.

**TAB\_EVENTS\_START\_ONLY**: `boolean`

When `true`, the event swapTab will only be planned on the start activity.

This is useful to speed up exploration for applications that always show the same tab widget all along. In case of doubt, leave it to `false`.

**EVENT\_WHEN\_NO\_ID**: `boolean`

When `true`, events will be fired on widgets with no assigned id. The target widget will be identified by type and name.

Events will still not be fired on widgets with no id and no name.

**MAX\_TASKS\_PER\_EVENT**: <not yet implemented> {default = 1}

# Storage parameters #

Following are the entries of the map stored in the `storage` sub-node of the XML file.

**ENABLE\_RESUME**: `boolean`

When `true`, the ripper stores its internal state after every trace, in order to be able to resume the session after any occurrence of an interrupting event (e.g. crashes of the software under test, failures of the Ripper, crashes of the Dalvik VM, crashes of the Android emulator and so on...)

When `false`, the ripping sessions definitively terminates after any such interruption, potentially leaving entire branches of the GUI tree unexplored. On the other hand, the ripping proceeds faster.

**MAX\_TRACES\_IN\_RAM**: `int` {0 = infinite}

When set to a value other than zero, forces the GUI Tree to be saved incrementally to the disk. The traces are flushed to the XML output file and removed from the device RAM when the model reaches the specified dimension.

Note that **ENABLE\_RESUME** = `true` forces **MAX\_TRACES\_IN\_RAM** = `1`

**FILE\_NAME**: `String` {default = "guitree.xml"}

The name of the XML file containing the GUI Tree model.

**TASK\_LIST\_FILE\_NAME**: `String` {default = "tasklist.xml"}

The name of the XML file containing the task list. This is a temporary file and is deleted when the ripping session is over.

Change this file name only in case of name conflicts with existing files.

**ACTIVITY\_LIST\_FILE\_NAME**: `String` {default = "activities.xml"}

The name of the file containing the Activity Map.

**PARAMETERS\_FILE\_NAME**: `String` {default = "parameters.obj"}

The name of the file containing the parameters saved by the Ripper components. This is a temporary file and is deleted when the ripping session is over.

Change this file name only in case of name conflicts with existing files.

**ACTIVITY\_DESCRIPTION\_IN\_SESSION**: `boolean`

When `false` the output will consist of two files: an Activity Map, with a detailed abstract description of the GUIs (activities) discovered by the Ripper during the exploration, and a GUI Tree, a (finite acyclic) state model of the application under test describing its transitions from a GUI to another in response to user interactions and other events.

When `true`, the output will consist of a single, usually much bigger, XML file (the GUI Tree) describing the whole model of the GUI of the application under test. This is only useful for debugging purposes.

Due to implementation issues, **ACTIVITY\_DESCRIPTION\_IN\_SESSION** = `false` forces **ENABLE\_RESUME** = `true`

**ENABLE\_MODEL**: `boolean`

When `true`, the ripper creates the **FILE\_NAME**.

**ONLY\_FINALTRANSITION**: `boolean` {default = "false"}

If it is `true`, in Tasklist is stored only the event to execute without the full path. **ENABLE\_MODEL** = `false` and **ONLY\_FINALTRANSITION** = `true`, they allow to speed up the test Random.

# Comparator parameters #

Following are the entries of the map stored in the `comparator` sub-node of the XML file.

**COMPARATOR\_TYPE**: `String`

This parameter defines the state equivalence criterion. Allowed values are:
  * `NullComparator`: no comparation, two states are always different (this is the default)
  * `NameComparator`: two states are equivalent if they share the same name (they are instances of the same Activity)
  * `CustomWidgetsSimpleComparator`: two states are equivalent if they share the same set of widgets (each widget in state A is equivalent to a widget in state B and viceversa)
  * `CustomWidgetsIntensiveComparator`: two states are equivalent if they share the same set of widgets, and the equivalent widgets share the same properties

For instance, if state A has an Edit Text with id 1234, `CustomWidgetsSimpleComparator` requires that state B has an Edit Text with id 1234 in order to consider B equivalent to A. `CustomWidgetsIntensiveComparator` additionally requires that the Edit Text has the same hint text.

**WIDGET\_TYPES**: `String[]`

Defines the subset of widget (types) on which `CustomWidgetsSimpleComparator` and `CustomWidgetsIntensiveComparator` operate. Ignored by `NameComparator`.

**COMPARE\_ACTIVITY\_NAME**: `boolean`

When `true`, name comparation is performed when using `CustomWidgetsSimpleComparator` or `CustomWidgetsIntensiveComparator` too. That is, two states are the same if they share the same name and the same set of widgets (and properties for the deep one).

This parameter mainly exists because sometimes the Ripper mistakenly detects an incorrect Activity name, usually in tabbed applications. In these cases it's advisable to set it to `false`, since the name attribute is not reliable.

In any other case it should be left to `true`.

**COMPARE\_STATE\_TITLE**: `boolean`

When `true`, `NameComparator` also checks whether two states have the same title. That is: two states are the same if they share the same name and title.

If **COMPARE\_ACTIVITY\_NAME** = `true`, this also applies for the other Comparator types.

**COMPARE\_LIST\_COUNT**: `boolean`

When `true` `CustomWidgetsSimpleComparator` and `CustomWidgetsIntensiveComparator` will consider two List View to be equivalent only if they have the same size. Defaults to `false`.

**COMPARE\_MENU\_COUNT**: `boolean`

When `true` `CustomWidgetsSimpleComparator` and `CustomWidgetsIntensiveComparator` will consider two Menu View to be equivalent only if they have the same number of items. Defaults to `true`.

**COMPARE\_VALUES**: `boolean`

When `true`, `CustomWidgetsIntensiveComparator` will consider two widgets to be equivalent only if they have the same value.

For instance, if state A has a Rating Bar with id 4567, name "Vote for this book" and sat to the value of "4 stars", state B will not be equivalent to A unless it has a Rating Bar with the same id and the same name which is set to the same value.

# Strategy parameters #

Following are the entries of the map stored in the `strategy` sub-node of the XML file.

**MAX\_NUM\_TRACES**: `int` {0 = infinite}

Terminates the ripping session after processing this amount of tasks.

**PAUSE\_AFTER\_TRACES**: `int` {0 = infinite}

Pauses the ripping session after processing this amount of tasks. The session can be resumed if **ENABLE\_RESUME** =  `true`

This is useful to restore the the Android device to a known state before processing the next task. In this case it should be set to `1` (pause after every trace)

**MAX\_TIME\_CRAWLING**: `long` {0 = infinite}

Terminates the ripping session after the given amount of seconds elapsed.

Note that the running task will not be interrupted when the timeout occurs: the termination is performed at the end of the last task.

**PAUSE\_AFTER\_TIME**: `long` {0 = infinite}

Pauses the ripping session after the given amount of seconds elapsed. The session can be resumed if **ENABLE\_RESUME** =  `true`

**TRACE\_MAX\_DEPTH**: `int` {0 = infinite}

Limits the exploration of the GUI to a certain depth of the resulting GUI Tree.

When **TRACE\_MAX\_DEPTH** = `n>0`, GUIs resulting from the execution of `n` actions (that is, nodes at depth `n` in the GUI Tree) won't be further explored, even when they're new and events could be fired on them.

**TRACE\_MIN\_DEPTH**: `int`

Forces the exploration of the GUI up to a certain depth, as long as there are events to be fired: the exploration criteria will be ignored until depth becomes greater than this parameter.

**AFTER\_EVENT\_DONT\_EXPLORE**: `String []`

A list of descriptions of "forbidding" events: that is, exploration of the states resulting from firing these events will be denied by the Ripper.

In other words: when an event is fired that matches one of the given descriptions, the exploration stops even if the new state has never been explored before.

(The description of an event is the text shown in target widget, if the target is a Text View, or in the first non empty Text View contained in the target widget, if the target is a Group View.)

**AFTER\_WIDGET\_DONT\_EXPLORE**: `int []`

A list of IDs of widgets that forbid the exploration of the states resulting from firing an event on them.

In other words: when an event is fired on a widget whose ID is in the list, the exploration stops even if the new state has never been explored before.

**CHECK\_FOR\_TRANSITION**: `boolean`

When `true`, the ripper will require that the start and final state for an action won't be equivalent to each other.

In other words, self-transition will not be stored in the output Gui Tree.

It's generally better left to `false`.

**EXPLORE\_ONLY\_NEW\_STATES**: `boolean`

When `true`, states that are equivalent to previous one (according to the defined Comparator) won't be explored.

The Random engine forces **EXPLORE\_ONLY\_NEW\_STATES** = `false` for obvious reasons.

When set to `false`, either **TRACE\_MAX\_DEPTH** or **MAX\_NUM\_TRACES** should be provided. Otherwise, there won't be any termination criterion, and the exploration will continue indefinitely.