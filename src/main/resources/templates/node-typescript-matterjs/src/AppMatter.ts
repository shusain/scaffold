import { Bodies, Composite, Engine, Render, Runner } from "matter-js";

export default class App {
    constructor() {

        window.addEventListener('keydown', (event)=>{ console.log(event)})
        window.addEventListener('keyup', (event)=>{ console.log(event)})

        // The matter Engine handles updating the virtual position and other
        // physics related properties of the objects in the World
        var engine = Engine.create();

        // The matter Renderer is a simple debug view of the state of the objects
        // in the World.
        var render = Render.create({
            element: document.body,
            engine: engine
        });

    
        // In matter all objects which have physics (gravity/collision) applied
        // to them are represented as "Bodies" in the World.  Each Body has an
        // associated collidor (circle, rectangle/other polygon) that defines
        // it's "hull" or "shell" (the part that generates collisions).

        // create two boxes and a ground
        var boxA = Bodies.rectangle(400, 200, 80, 80);
        var boxB = Bodies.rectangle(450, 50, 80, 80);
        var ground = Bodies.rectangle(400, 610, 810, 60, { isStatic: true });

        // add all of the bodies to the world
        Composite.add(engine.world, [boxA, boxB, ground]);

        // We first initialize our renderer
        Render.run(render);

        // Then initialize a "runner" that will drive physics ticks and allow the Engine
        // to update the world based on an interval of time elapsed since last tick.
        // Again this is mostly meant for debug purposes or developing simple games that
        // don't require more complex sprite caching mechanisms etc.
        var runner = Runner.create();

        // Finally run the engine with our runner
        Runner.run(runner, engine);
    }
}

new App()