import { Application, Assets, Rectangle, Sprite } from 'pixi.js';

async function init ()
{
    // Create a new application
    const app = new Application();

    // Initialize the application
    await app.init({ background: '#1099bb', resizeTo: window, antialias:true});

    // Append the application canvas to the document body
    document.body.appendChild(app.canvas);

    // Setting up global stage click handler
    app.stage.eventMode = "static"
    app.stage.interactive = true
    app.stage.hitArea = new Rectangle(0, 0, window.screen.width, window.screen.height)

    const texture = await Assets.load('https://pixijs.com/assets/bunny.png');

    // Create a bunny Sprite
    const bunny = new Sprite(texture);

    // Center the sprite's anchor point
    bunny.anchor.set(0.5);

    // Move the sprite to the center of the screen
    bunny.x = app.screen.width / 2;
    bunny.y = app.screen.height / 2;

    app.stage.addChild(bunny);

    // Listen for animate update
    app.ticker.add((time) =>
    {
        // Just for fun, let's rotate mr rabbit a little.
        // * Delta is 1 if running at 100% performance *
        // * Creates frame-independent transformation *
        bunny.rotation += 0.1 * time.deltaTime;
    });
};

init();