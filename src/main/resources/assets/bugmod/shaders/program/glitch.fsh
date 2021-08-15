#version 120
uniform float Time;
uniform sampler2D DiffuseSampler;
varying vec2 texCoord;

// https://www.shadertoy.com/view/MlVSD3
float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453) * 2.0 - 1.0;
}

float offset(float blocks, vec2 uv) {
    return rand(vec2(Time, floor(uv.y * blocks)));
}

void main()
{
    //gl_FragColor = texture2D(DiffuseSampler, texCoord);
    //gl_FragColor.r = texture2D(DiffuseSampler, texCoord + vec2(offset(16.0, texCoord) * 0.05, 0.0)).r;
    //gl_FragColor.g = texture2D(DiffuseSampler, texCoord + vec2(offset(8.0, texCoord) * 0.05 * 0.16666666, 0.0)).g;
    //gl_FragColor.b = texture2D(DiffuseSampler, texCoord + vec2(offset(8.0, texCoord) * 0.05, 0.0)).b;

    gl_FragColor = texture2D(DiffuseSampler, texCoord + vec2(offset(200.0, texCoord) * 0.02, 0.0));
}

//https://www.shadertoy.com/view/lslcDn
//void main()
//{
//    float POWER = 0.5; // How much the effect can spread horizontally
//    float VERTICAL_SPREAD = 7.0; // How vertically is the sin wave spread
//    float ANIM_SPEED = 1.0f; // Animation speed
//
//    vec2 uv = texCoord;
//    float y = (uv.y + Time * ANIM_SPEED) * VERTICAL_SPREAD;
//
//    uv.x += (
//    // This is the heart of the effect, feel free to modify
//    // The sin functions here or add more to make it more complex
//    // and less regular
//    sin(y)
//    + sin(y * 5.0) * 0.2
//    + sin(y * 20.0) * 0.03
//    )
//    * POWER // Limit by maximum spread
//    * sin(uv.y * 3.14) // Disable on edges / make the spread a bell curve
//    * sin(Time); // And make the power change in time
//
//    gl_FragColor = texture2D(DiffuseSampler, uv);
//}