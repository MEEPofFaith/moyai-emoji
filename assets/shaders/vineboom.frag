#define HIGHP

uniform sampler2D u_texture;

uniform vec2 u_resolution;
uniform float u_radius;
uniform float u_intensity;

varying vec2 v_texCoords;

void main(){
    if(u_radius < 0.01 || u_intensity < 0.01){
        gl_FragColor = texture2D(u_texture, v_texCoords.xy);
        return;
    }

    vec2 c = v_texCoords.xy - vec2(0.5);
    vec2 coords = vec2(c.x * u_resolution.x, c.y * u_resolution.y);

    float offset = sqrt(coords.x * coords.x + coords.y * coords.y) / u_radius * u_intensity;
    float ang = atan(coords.y, coords.x);

    vec2 from = coords - vec2(offset * cos(ang), offset * sin(ang));
    from /= u_resolution;
    from += 0.5;

    gl_FragColor = texture2D(u_texture, from.xy);
}
