package com.bagevent.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.bagevent.db.BackupCollection;
import com.bagevent.db.CollectChild;

public class ExcelUtil {
	//内存地址
	public static String root = Environment.getExternalStorageDirectory()
			.getPath();

	public static void writeExcel(Context context, List<CollectChild> exportOrder,List<BackupCollection> mBack,
			String fileName) throws Exception {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&getAvailableStorage()>1000000) {
			//Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
			Log.e("write err","SD is Not available");
			return;
		}
		String[] title = {"eventId","collectionId","姓名", "Barcode","collectTime","是否同步"};
		File file;
		File dir = new File(context.getExternalFilesDir(null).getPath());
		file = new File(dir, fileName + ".xls");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 创建Excel工作表
		WritableWorkbook wwb;
		OutputStream os = new FileOutputStream(file);
		wwb = Workbook.createWorkbook(os);
		// 添加第一个工作表并设置第一个Sheet的名字
		WritableSheet sheet = wwb.createSheet("采集数据", 0);
		Label label;
		for (int i = 0; i < title.length; i++) {
			// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
						// 在Label对象的子对象中指明单元格的位置和内容
			label = new Label(i, 0, title[i], getHeader());
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}

		if(exportOrder != null) {
			for (int i = 0; i < exportOrder.size(); i++) {
				CollectChild order = exportOrder.get(i);
				Label orderNum = new Label(0, i + 1, order.eventId+"");
				Label restaurant = new Label(1, i + 1, order.collectionId+"");
			//	Label name = new Label(2, i + 1, order.attendeeName+"");
				Label barcods = new Label(3, i + 1, order.attendeeBarcode);
				Label time = new Label(4, i + 1, order.attendeeCheckInTime);
				Label sync = new Label(5,i + 1,order.collectIsSync +"");
				sheet.addCell(orderNum);
				sheet.addCell(restaurant);
				//sheet.addCell();
				sheet.addCell(barcods);
				sheet.addCell(time);
				sheet.addCell(sync);
			}
			//Toast.makeText(context, "写入成功" + dir + fileName, Toast.LENGTH_LONG).show();
		}else {
			for (int j = 0; j < mBack.size(); j++) {
				BackupCollection order = mBack.get(j);
				Label orderNum = new Label(0, j + 1, order.eventId+"");
				Label restaurant = new Label(1, j + 1, order.collectionId+"");
				Label name = new Label(2, j + 1, order.attendeeName+"");
				Label barcods = new Label(3, j + 1, order.attendeeBarcode);
				Label time = new Label(4, j + 1, order.attendeeCheckInTime);
				sheet.addCell(orderNum);
				sheet.addCell(restaurant);
				sheet.addCell(name);
				sheet.addCell(barcods);
				sheet.addCell(time);
			//	Toast.makeText(context, "写入成功", Toast.LENGTH_LONG).show();
			}
		}


		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
	}

	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD);// 定义字体
		try {
			font.setColour(Colour.BLUE);// 蓝色字体
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			// format.setBorder(Border.ALL, BorderLineStyle.THIN,
			// Colour.BLACK);// 黑色边框
			// format.setBackground(Colour.YELLOW);// 黄色背景
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}
	
	/** 获取SD可用容量 */
	private static long getAvailableStorage() {

		StatFs statFs = new StatFs(root);
		long blockSize = statFs.getBlockSize();
		long availableBlocks = statFs.getAvailableBlocks();
		long availableSize = blockSize * availableBlocks;
		// Formatter.formatFileSize(context, availableSize);
		return availableSize;
	}
}
