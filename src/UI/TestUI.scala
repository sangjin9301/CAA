package UI

import Engine.CompareFreq_Location
import Engine.Preprocessor
import javax.swing.JFrame
import javax.swing.JComboBox
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JLabel
import javax.swing.JTextField
import java.awt.GridLayout
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import scala.collection.immutable.List

object TestUI {

  def main(args: Array[String]): Unit =
    {
      println("start")

      var userList = new java.util.Vector[String](10, 1)
      userList.addElement("03e2c434-dbfc-4ccf-9339-80eaf2cdc241")
      userList.addElement("1930cb21-38f7-465a-be9a-e7c22d89d2ca")
      userList.addElement("1f43492e-79b5-4253-ac38-667930ad7334")
      userList.addElement("208aeee9-177b-42e4-b647-f8613af6c2a9")
      userList.addElement("e26edaae-ebb2-4c14-8575-f2335fc857ae")
      userList.addElement("bde8f3a0-6a66-48bb-91c9-54a434a4c8bd")
      userList.addElement("a082a18f-9af2-4972-89ee-ccbbffb4647c")
      userList.addElement("a71c1917-7a94-4b2d-be2f-5be61c0415bb")
      userList.addElement("9e797eb1-1ecb-424f-a0b0-31a0bd85a763")
      userList.addElement("7e1164d1-4bea-42f6-9242-4972da743021")
      userList.addElement("7aa65059-7d66-4dbb-b1ec-d96e48bf4e9c")
      userList.addElement("5e4ff80d-7684-420f-a5c3-a1de8dba3b9f")
      userList.addElement("5aa8efa1-a627-4d37-a208-a89f567100d9")
      userList.addElement("58a56cf1-6a59-4386-a605-49ebc37a94c7")
      userList.addElement("4e5db50b-6501-42c6-a789-b7122cfc47ed")
      userList.addElement("4dee3b8e-5f10-4b23-b89f-190d0ee4574c")
      userList.addElement("40a217cd-0a7d-4d55-aa70-b68ec699dfbe")
      userList.addElement("3e476476-36a9-40d8-897d-d9d716e2548d")
      userList.addElement("36892a03-594f-4f43-ab32-eed03214e306")
      userList.addElement("227ed4b7-04c8-4cad-9712-b4408ad3197f")
      userList.addElement("e5817b97-cb56-471e-a202-ee2b61a2bcd9")

      var Score: Double = 0
      var processor = new Preprocessor
      var rule = new CompareFreq_Location

      val frame = new JFrame
      frame.setTitle("Prototype")
      frame.setSize(500, 150)
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
      val panel = new JPanel
      panel.setLayout(new GridLayout(4, 2))

      val req_label = new JLabel("Request UserKey : ")
      val base_label = new JLabel("Base UserKey : ")
      val score_label = new JLabel("Score : ")
      val result_label = new JLabel(" 0 ")
      //      var req_text = new JTextField; req_text.setSize(50, 500); req_text.setEnabled(true)
      var req_text = new JComboBox(userList); req_text.setSize(50, 500); req_text.setEnabled(true)
      var base_text = new JComboBox(userList); base_text.setSize(50, 500); base_text.setEnabled(true)
      var accept_btn = new JButton("ACCEPT")
      accept_btn.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit =
          {
            processor.getData(false, "location", req_text.getSelectedItem.toString())
            processor.getData(true, "location", base_text.getSelectedItem.toString())
            Score += rule.compare
            result_label.setText(Score.toString())
          }
      })
      var cancle_btn = new JButton("CANCLE")
      cancle_btn.addActionListener(new ActionListener {
        override def actionPerformed(e: ActionEvent): Unit =
          {
            JFrame.EXIT_ON_CLOSE
          }
      })

      panel.add(req_label)
      panel.add(req_text)
      panel.add(base_label)
      panel.add(base_text)
      panel.add(score_label)
      panel.add(result_label)
      panel.add(accept_btn)
      panel.add(cancle_btn)
      panel.setVisible(true)

      frame.add(panel)
      frame.setVisible(true)
      frame.setEnabled(true)
      frame.setResizable(false)
    }

}