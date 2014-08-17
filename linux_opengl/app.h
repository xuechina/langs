#ifndef APP_H_
#define APP_H_

#include <GL/glu.h>

class Application
{
public:
    static void Init()
    {
        glClearColor(0.0, 0.0, 0.0, 0.0);
        glMatrixMode(GL_PROJECTION);
        glOrtho(-5, 5, -5, 5, 5, 15);
        glMatrixMode(GL_MODELVIEW);
        gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
    }

    static void display()
    {
        glClear(GL_COLOR_BUFFER_BIT);

        glColor3f(1.0, 0, 0);
        //glutWireTeapot(3);
        glutWireIcosahedron();

        glFlush();
    }
};


#endif /* APP_H_ */
