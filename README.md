
# Customizable Cellular Automaton

This is an engine that allows the customization and simulation of almost any cellular automaton.


## How to navigate the simulator

Once the rules for the customized cellular automaton are written and the simulator launched, you will see a black screen with a few menu buttons.
You can use the WASD or the arrow keys to move around in the simulator. You can also zoom and unzoom with the mouse wheel.
On the top right, there is a cross button to close the simulation (you can also press the escape key).
On the bottom right, you can manipulate how many steps are to be simulated every second using the **-** and the **+** buttons (you can also press the same keys on your keyboard). You can also pause the game with the rightmost button (you can also press the space key).
On the bottom left, the first button is used to centre the camera back to the origin point. By default, clicking anywhere on the simulation will change the state of the corresponding cell. Clicking the same cell will cycle the state of that cell. With the second button, you can set which state will be put by clicking on a cell or if the state should cycle.

When the simulation is played, for every step, all existing cells will be checked. Every transition rule will check if it applies for every cell and if it does, it will tell the simulator what change should be made before the next step. There are no bounds for the simulation, except the performances of the computer it runs on.

## Creating your custom rules

To create custom rules, you will have to create a personalized JSON file. The file ``rules.json`` is an example of every feature possible, and we will use it to explain how to write customized rules.

### Making custom states
- A cell can have multiple states, that is represented using colour in the simulator. The first state, with the colour black, is mandatory and not written in the JSON file. This state should not have any rules that make it interact with itself. It is a quiescent state.
- A state is only defined by its colour. In the "states" array, every element should have three int values named "red", "green" and "blue", ranging from 0 to 255.
- The element ``{"red":255,"green":255,"blue":255}`` represents a new custom state in the colour white.
- States are numbered in the same order as they appear in the array. So the state numbered 0 is black, the state numbered 1 is the first custom state, etc.

### Making custom neighbours
- The neighbours of a cell are what a cell will check in order to know if it has to change states. A cell will not be capable of looking outside its neighbours to change states.
- A neighbouring cell is defined by relative coordinates. In the "adjacency" array, every element should have two int values named "x" and "y". 
- Having the element ``{"x":0,"y":1}`` means that every cell has the cell directly on top of it in his neighbours.

### Making custom transitions
Currently, there are three types of transition. Each will provide a way for a cell to change states according to its neighbours. We will look in the "transition" object for the three arrays corresponding to the three types of transition.

1. **Transition by default**: 
- With this transition, a cell will ignore its neighbours and change from one state to another. 
- In the "default" array, every element should have two int values named "initialState" and "resultingState", with the number corresponding to a state. 
- Having the element ``{"initialState":0,"resultingState":1}`` will change all black cells into the first custom state in one step.

2. **Transition by extension**: 
- With this transition, a cell will look at specific neighbours and check if their state corresponds to this transition rule. If it does, that cell will change its state. 
- In the "extension" array, every element should have two int values named "initialState" and "resultingState", with the number corresponding to a state. It also should have an array named "statePositions". In this array, every element should have three int values named "state", "x" and "y", with the "state" being the state to check and the "x" and "y" being the relative coordinates. These coordinates must be included within the neighbourhood.
- Having the element ``{"initialState":0,"resultingState":1,"statePositions":[{"state":1,"x":0,"y":1}]}`` will change all black cells into the first custom state if the cell directly on top of it is also in the first custom state.

3. **Transition by enumeration**: 
- With this transition, a cell will count the number of states in its neighbourhood and check if it is coherent with the transition rule. If it is, that cell will change its state.
- In the "enumeration" array, every element should have two int values named "initialState" and "resultingState", with the number corresponding to a state. It also should have an array named "stateQuantities". In this array, every element should have two int values named "state" and "quantity", with the "state" being the state to check and the "quantity" being the exact amount required of the state.
- Having the element ``{"initialState":0, "resultingState":1, "stateQuantities":[{"state":1, "quantity":3}]}`` will change all black cells into the first custom state if there are exactly three cells in the first custom state in its neighbourhood.
- Optionally, you can add a custom neighbourhood specially for this transition rule. That means that the cell will only check if that new neighbourhood is coherent with the transition rule. That neighbourhood must be a subset of the true neighbourhood.
- In the "enumeration" array, if you want a custom neighbourhood, an element should have all the necessary things for a transition by enumeration. In addition, it should have an array named "adjacencySubset". In this array, every element should have two int values named "x" and "y", being the relative coordinates that make up the custom neighbourhood. These coordinates must be included within the neighbourhood.
- Having the element ``{"initialState":0, "resultingState":1, "stateQuantities":[{"state":1, "quantity":1}],"adjacencySubset":[{"x":0, "y":1},{"x" 0, "y":-1}]}`` will change all black cells into the first custom state if there is exactly one cell in the first custom state in its custom neighbourhood. That custom neighbourhood is composed of the cell directly above and the cell directly below.

### The example
The JSON file of the example can be found in the file named ``rules.json``.
In this example, the cellular automaton has six different states. The neighbours of a cell are the adjacent cells, orthogonally and diagonally.

Here is the list of the states with explanations on how they interact:
1. **Black**, it's the quiescent state, it does nothing when on its own. 
2. **White**, it works just like with the game of life (a black cell becomes white if exactly three neighbouring cells are white, a white cell becomes black if there is one or less neighbouring white cells or four or more neighbouring white cells).
These rules are defined using eight different transitions by enumeration. The first one changes a black cell to white if exactly three of its neighbours are white. The seven other transitions change a white cell to black if 0, 1, 4, 5, 6, 7 or 8 of its neighbours are white.
3. **Gray**, a black cell will become gray if a gray cell is directly below or above it.
These rules are defined using two different transitions by enumeration, with the option of having a different neighbourhood. Both transitions change the neighbourhood to be the cells directly above and below. The first transition changes a black cell to gray if there is exactly one gray cell in its neighbourhood. The second changes a black cell to gray if there are exactly two gray cells in its neighbourhood.
4. **Green**, a green cell will become a blue.
This rule is defined using a transition by default. If a cell is green, the transition makes it blue.
5. **Blue**, a blue cell will become a green.
This rule is defined using a transition by default. If a cell is blue, the transition makes it green.
6. **Red**, a black cell will become red if there is a red cell on the top left of it and if there is a black cell on the top right of it.
This rule is defined using a transition by extension. It does exactly what is described above.
