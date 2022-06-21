# Grade 11 ISU (ICS3U1) 
### _**Made by Cyrus Choi and Christian Fisla**_

#### **Responsibilities of each Person**    

Cyrus: 
- Initialized rectangle array for all ghosts and towers
- Made and implemented click for title screen, about us and How to Play
- Coded updateBullet array where the bullets are supposed to move in the direction of the ghosts
- Coded part of moveEnemy array to move the enemy along a certain path on the X and Y axis
- Wrote handleAction array to run certain chunks of code based on where the user has clicked
- Initialized background music, wave counter, money counter, window icon

Christian: 
- Included multiple images at once (using buffered image)
- Initialized our FPS counter and managed thread sleep times
- Created getTheta() to calculate the angle from the bullet to the enmy and the tower to the enemy
- Created rotateImage() that reads in an angle in degrees and rotates and returns a BufferedImage
- Created getSlope() which determines the bullet path using the slope of the hypotenuse 
- Collision for bullets + enemies
- Implemented cost of tower and dynamic rates for money
- Art for turret and map

#### **Missing Functionalities**
- Missing other levels
- Aimed for multiple towers and upgrade options

#### **Additional Functionalities**
- Made the tower turret rotate to track each enemy
- Added click sound
- You can place towers on any square in the grid aside for the track
- Tower bullets that have a certain "pierce" (1 pierce => bullet goes through one enemy, 2 pierce => bullet goes through two enemies, etc...)

#### **Known bugs / Errors**
- crashes once it reaches the end amount of enemies

#### **Other important Information**
- N/A
