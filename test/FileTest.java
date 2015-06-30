import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class FileTest {
	
	@Test
	public void test() throws IOException{ 
		File oldFile = new File("c:/abc.jar");
		if (oldFile.exists()) {
			System.out.println("修改前文件名称是：" + oldFile.getName());
			String rootPath = oldFile.getParent();
			System.out.println("根路径是：" + rootPath);
			File newFile = new File(rootPath + File.separator + "PMSTmp");
			System.out.println("修改后文件名称是：" + newFile.getName());
			if (oldFile.renameTo(newFile)) {
				System.out.println("修改成功!");
			} else {
				System.out.println("修改失败");
			} 
		}
	}

	public static void main(String[] args) {
		
		
		
		File file = new File("c:\\abc.jar");
		
		
		System.out.println(file.getPath());
	}
}
