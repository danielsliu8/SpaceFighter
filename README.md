SpaceFighter is a personal project of mine I recently started. It's basically a generic Space Invaders clone. You control a little space ship that shoots stuff at incoming invaders that come in endless waves. Controls: WASD for controlling your space ship, J and K to shoot 1 and 3 projectiles, respectively, and Q to quit.

Friends and observers have remarked that SpaceFighter is a very existential game (mostly due to its incomplete nature). You control a little space ship confined within the boundaries of a 600 by 350 grid, stuck endlessly shooting invaders and wandering around with no defined goal or purpose. You can kill as many invaders as you like but they keep coming. You accomplish nothing. They don't even seem to care that you're shooting them. And how are you know you're even supposed to be shooting them? Such is the nature of SpaceFighter.

There are some difficulties I'm having with the main game loop as well, including, but not limited to:
 - controlling the speed of the game
 - efficiency in detecting collisions

Right now I'm controlling the speed of the game by just calling Thread.sleep(), which I know is bad, and I intend on implementing a clock for the game to run by instead. Running the game also takes a heavy toll on the processor -- testing for collisions between invaders and projectiles right now is just very inefficient. So be careful when running the game that you're not running to much stuff in the background or your computer will not be happy.

As of right now, SpaceFighter is still not very well documented either.