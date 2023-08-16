# vexutils
Lightweight utility plugin

## Features
### Teleport Requesting (TPA)
Being that teleporting to your friends is a fairly common need among servers, teleport requesting (or TPA-ing) has been implemented.
To teleport to a friend, simply type `/tpa <friendname>` to send them a request.

![image](https://github.com/vexeval/vexutils/assets/90877660/2071c856-6b90-4b71-84e8-3f518c896637)

The friend will then receive a message informing them of the request, and display their option of accepting or denying.

![image](https://github.com/vexeval/vexutils/assets/90877660/3b343ca2-d95a-4b53-9432-fbad0069ed6e)

Upon accepting, the requestor will teleport to them

![image](https://github.com/vexeval/vexutils/assets/90877660/d8de4dcb-9c18-4713-9782-1609c0ef0004)

*permissions*
```
tpa.*
tpa.use
tpa.accept
tpa.cancel
tpa.deny
```

### Homes
Have a place to set down your bed and crafting table? Let's make it easy to get back there.
To set a home at your current position, simply type `/sethome <name>`.

![image](https://github.com/vexeval/vexutils/assets/90877660/4ded663a-6dac-4ac6-89ad-a28db1d10f58)

To teleport back to this home, run `/home <name>`

![image](https://github.com/vexeval/vexutils/assets/90877660/88adb563-e073-419c-b634-4f6c1dada44a)

And if you ever don't want a home again, simply remove it with `/delhome <name>`

![image](https://github.com/vexeval/vexutils/assets/90877660/04c5e39c-42e7-4132-802f-c08fb2cfb4e1)

*permissions*
```
home.*
home.add
home.del
home.use
```

### VSigns
A useful trinket to some (possibly linking large or far-apart bases) is to have a way to warp around their base without having to manually run home commands each time.

Setting one up is easy. Simply place a sign of any variety, enter the identifier `[vsign]` at the first line, and write the coordinates on the second! Beyond that, the rest of the sign's free real estate.

![sign1](https://github.com/vexeval/vexutils/assets/90877660/860d1c5a-06e3-4d7c-88d9-9b404915cfa2)

Once completed, the sign will teleport anyone who interacts with it to the coordinates displayed on its front panel.

*Permissions*
```
vsign.*
vsign.use
```
