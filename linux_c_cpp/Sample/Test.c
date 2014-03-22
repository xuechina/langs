void test(){
	char s1[]={'a', 'b', '\0'};
	char s2[]={'c', 'd'};
	puts(s2);//输出cdabc.因为在内存中变量分配是反向的，先是s2，然后s1,所以会输出cdabc.
}